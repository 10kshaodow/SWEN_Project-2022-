import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartService } from 'src/app/services/cart/cart.service';
import { HealthReportService } from 'src/app/services/health-report/health-report.service';
import { NavigationService } from 'src/app/services/navigation/navigation.service';
import { OrderService } from 'src/app/services/order/order.service';
import { ProductService } from 'src/app/services/product/product.service';
import { ScrollService } from 'src/app/services/scroll/scroll.service';
import { UserService } from 'src/app/services/user/user.service';
import { HealthReport } from 'src/app/types/HealthReport';
import { Product } from 'src/app/types/Product';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-view-health-report',
  templateUrl: './view-health-report.component.html',
  styleUrls: ['./view-health-report.component.css'],
})
export class ViewHealthReportComponent implements OnInit {
  isOrnitholo: boolean = false;
  isAdmin: boolean = false;
  outOfStock: boolean = false;
  showModal: boolean = false;

  user?: User;
  sponsorshipDate?: Date;

  product!: Product;
  healthReport!: HealthReport;

  displaySource?: string;
  displayIndex: number = 0;

  constructor(
    private navigation: NavigationService,
    private healthService: HealthReportService,
    private route: ActivatedRoute,
    private productService: ProductService,
    private userService: UserService,
    private scrollService: ScrollService,
    private orderService: OrderService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (!signedIn) return;
      this.getCurrentUser();
    });

    this.getCurrentUser();
    this.getHealthReport();
  }

  // see if logged in user can update report
  isOrnithologist() {
    this.isOrnitholo =
      this.userService.isSignedIn() &&
      this.userService.getCurrentUser().type == UserType.Ornithologist;

    this.isAdmin =
      this.userService.isSignedIn() &&
      this.userService.getCurrentUser().type == UserType.Admin;
  }

  /**
   * Set the current user variable
   */
  getCurrentUser(): void {
    if (!this.userService.isSignedIn()) return;

    this.user = this.userService.getCurrentUser();
    this.doesUserHaveProductInCart();
    this.isOrnithologist();
  }

  /**
   * check if a user has this product allready
   * in their cart and set the out of stock
   * to true if cart amount is greater
   * than or equal products quantity
   */
  doesUserHaveProductInCart() {
    this.cartService.getCart();
    let quantity = this.howManyInCart();

    if (this.product && quantity >= this.product.quantity) {
      this.outOfStock = true;
    }
  }

  // ask server for the specified health report
  getHealthReport() {
    let id = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.healthService.getHealthReport(id).subscribe((report) => {
      if (report) {
        this.healthReport = report;
        this.getProduct(report.falconID);
        this.setDisplaySource();
        this.startSlideShow();
      }
    });
  }

  // ask server for the specified product
  getProduct(id: number) {
    this.productService.getProduct(id).subscribe((prod) => {
      this.product = prod;
      this.doesUserHaveProductInCart();
      this.getSponsorshipDate();
    });
  }

  // set display source to current index
  setDisplaySource(source?: string) {
    this.displaySource =
      source ?? this.healthReport.fileName[this.displayIndex];

    if (!source) return;

    this.healthReport.fileName.forEach((value, i) => {
      if (value == source) this.displayIndex = i;
    });
  }

  startSlideShow() {
    setTimeout(() => {
      this.nextPic();
      if (this.healthReport.fileName.length > 1) {
        this.startSlideShow();
      }
    }, 5000);
  }

  // increase the index of the displayed image
  nextPic() {
    if (this.displayIndex >= this.healthReport.fileName.length - 1) {
      this.displayIndex = 0;
    } else {
      this.displayIndex++;
    }
    this.setDisplaySource();
  }

  // decrease the index of the diplayed image
  lastPic() {
    if (this.displayIndex == 0) {
      this.displayIndex = this.healthReport.fileName.length - 1;
    } else {
      this.displayIndex--;
    }
    this.setDisplaySource();
  }

  // turn a date into a formatted string
  dateTimeToString(dateTime: Date) {
    let date = new Date(dateTime);
    return date.toLocaleDateString();
  }

  // back to the product you came from
  // backToProduct() {
  //   if (this.healthReport) {
  //     if (this.isOrnitholo) {
  //       this.navigation.navigate([
  //         `ornithology-view/${this.healthReport.falconID}`,
  //       ]);
  //     } else {
  //       this.router.navigate([`/product-page/${this.healthReport.falconID}`]);
  //     }
  //   } else {
  //     this.router.navigate([`/home`]);
  //   }
  // }

  goBack() {
    this.navigation.back();
  }

  // go to the update report page of current health report
  toUpdateReport() {
    // this.router.navigate([
    //   `/create-health-report/${this.healthReport.falconID}/${this.healthReport.reportID}`,
    // ]);
    this.navigation.navigate(
      `/create/report/${this.product.id}/${this.healthReport.reportID}`
    );
  }

  /**
   * Returns True if current user is apart of products sponsor list
   */
  doesUserSponsorFalcon(): boolean {
    if (this.product.productType != 1) return false;

    if (!this.user) return false;

    for (let sponsor of this.product.sponsors) {
      if (sponsor === this.user.username) return true;
    }

    return false;
  }

  /**
   * Find the date that the current user sponsored the product
   */
  getSponsorshipDate() {
    if (!this.doesUserSponsorFalcon()) return;
    if (this.user && this.product) {
      for (let i = 0; i < this.user.orderHistory.length; i++) {
        let orderNumber = Number(this.user.orderHistory[i]);
        this.orderService.getOrder(orderNumber).subscribe((_order) => {
          console.log('Order id:' + _order.id);
          for (let j = 0; j < _order.orderItemList.length; j++) {
            if (_order.orderItemList[j].productID == this.product.id) {
              this.sponsorshipDate = _order.orderDate;
            }
          }
        });
      }
    }
  }

  // ask cart service to check how many of specified product are in the cart
  howManyInCart(): number {
    if (this.product) {
      let allreadyInCart = this.cartService.getOrderItemFromCart(
        this.product.id
      );
      if (allreadyInCart) return allreadyInCart.quantity;
    }
    return 0;
  }

  /**
   * Add specified product to a users cart as
   * long as it is not out of stock
   * then asks to show the modal to the user
   */
  addToCart(): void {
    if (this.outOfStock) {
      return;
    }

    let quantityPurchasing = this.howManyInCart();

    if (quantityPurchasing >= this.product.quantity) {
      this.outOfStock = true;
      return;
    }

    this.cartService.addToCart({
      productID: this.product.id,
      price: 0,
      quantity: 1,
    });

    if (
      this.product.quantity - this.howManyInCart() == 0 ||
      this.product.productType == 1
    ) {
      this.openModal();
    }
  }

  // Close the modal
  closeModal() {
    this.showModal = false;
    this.scrollService.setScroll(true);
  }

  // Open the modal
  openModal() {
    this.showModal = true;
    this.scrollService.setScroll(false);
  }

  // navigation function
  toHome() {
    this.closeModal();
    // this.router.navigate(['/']);
    this.navigation.navigate('/home');
  }

  // navigation function
  toCart() {
    this.closeModal();
    // this.router.navigate(['/cart']);
    this.navigation.navigate('/cart');
  }
}
