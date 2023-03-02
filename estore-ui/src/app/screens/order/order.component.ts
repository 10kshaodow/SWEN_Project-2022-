import { Component, Input, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order/order.service';
import { Order } from 'src/app/types/Order';
import { OrderStatus } from 'src/app/types/OrderStatus';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';
import { UserType } from 'src/app/types/UserType';
import { ProductService } from 'src/app/services/product/product.service';
import { NavigationService } from 'src/app/services/navigation/navigation.service';
import { User } from 'src/app/types/User';
import { ThisReceiver } from '@angular/compiler';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
})
export class OrderComponent implements OnInit {
  constructor(
    private orderService: OrderService,
    private userService: UserService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private navigation: NavigationService
  ) {}

  order!: Order;
  UnderReview: OrderStatus = OrderStatus.UnderReview;
  Fulfilled: OrderStatus = OrderStatus.Fulfilled;
  Cancelled: OrderStatus = OrderStatus.Cancelled;
  Ordered: OrderStatus = OrderStatus.Ordered;

  userSignedIn: boolean = false;
  user!: User;
  orderee!: User;

  isAdmin: boolean = false;

  ngOnInit(): void {
    this.getOrderFromParams();
    this.userService.userCurrentlySignedIn.subscribe((signedIn: boolean) => {
      if (!signedIn) {
        this.navigation.back();
        return;
      }

      this.userSignedIn = signedIn;
      this.user = this.userService.getCurrentUser();
      this.isUserAdmin();
    });

    if (!this.userService.isSignedIn()) {
      this.navigation.back();
      return;
    }

    this.userSignedIn = this.userService.isSignedIn();
    this.user = this.userService.getCurrentUser();
    this.isUserAdmin();
  }

  /**
   * Ask the server for the specified order details
   *
   * order is specified from the route parameters
   */
  private getOrderFromParams() {
    let paramId = this.route.snapshot.paramMap.get(`id`);
    if (paramId) {
      console.log(`Getting Order ${paramId}`);
      console.log();
      let id = Number(paramId);
      this.orderService.getOrder(id).subscribe((_order) => {
        this.order = _order;
        this.getOrderee(this.order.username);
      });
    }
  }

  private getOrderee(userName: string) {
    this.userService.getUser(userName).subscribe((_user) => {
      this.orderee = _user;
    });
  }

  // isUserSignedIn(): boolean {
  //   return this.userService.isSignedIn();
  // }

  isUserAdmin() {
    this.isAdmin = this.user.type === UserType.Admin;
  }

  // getCurrentUsername(): String {
  //   if (this.userSignedIn) {
  //     return this.userService.getCurrentUser().username;
  //   } else {
  //     return '';
  //   }
  // }

  fulfillOrder(): void {
    if (!this.isAdmin) return;
    this.order.status = OrderStatus.Fulfilled;
    this.orderService.updateOrder(this.order).subscribe(() => {
      console.log('Order Fulfilled');
    });
  }

  cancelOrder(): void {
    this.order.status = OrderStatus.Cancelled;
    this.orderService.updateOrder(this.order).subscribe(() => {
      console.log('Order Cancelled');
    });
    this.updateInventoryAfterCancel();
  }

  updateInventoryAfterCancel() {
    for (var orderItem of this.order.orderItemList) {
      let productID = orderItem.productID;
      let boughtQuantity = orderItem.quantity;
      this.productService.getProduct(productID).subscribe((_product) => {
        if (_product.productType == 1) {
          let sponsorIndex = this.findOrderSponsor(_product.sponsors);
          if (sponsorIndex != -1) {
            _product.sponsors.splice(sponsorIndex, 1);
          }
        } else if (_product.productType == 2) {
          let originalQuantity = _product.quantity;
          let updateQuantity = originalQuantity + boughtQuantity;
          _product.quantity = updateQuantity;
        }
        this.productService.updateProduct(_product).subscribe((__product) => {
          console.log('product Updated');
        });
      });
    }
  }

  findOrderSponsor(sponsors: String[]) {
    let sponsorIndex = -1;
    for (let i = 0; i < sponsors.length; i++) {
      if (sponsors[i] === this.order.username) {
        return i;
      }
    }

    return sponsorIndex;
  }

  goBack() {
    this.navigation.back();
  }
}
