import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Product } from 'src/app/types/Product';
import { ProductService } from 'src/app/services/product/product.service';

import { CartService } from 'src/app/services/cart/cart.service';
import { OrderItem } from 'src/app/types/OrderItem';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

import { HealthReport } from 'src/app/types/HealthReport';
import { HealthReportService } from 'src/app/services/health-report/health-report.service';
import { NavigationService } from 'src/app/services/navigation/navigation.service';
import { ScrollService } from 'src/app/services/scroll/scroll.service';
import { OrderService } from 'src/app/services/order/order.service';
import { ThisReceiver } from '@angular/compiler';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css'],
})
export class ProductPageComponent implements OnInit {
  product!: Product;
  user!: User;
  healthReports: HealthReport[] = [];
  sponsors: User[] = [];

  sponsorshipDate!: Date;

  isAdmin: boolean = false;
  isOrno: boolean = false;

  outOfStock: boolean = false;
  showModal: boolean = false;

  orderItem: OrderItem = {
    productID: 0,
    quantity: 0,
  } as OrderItem;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private orderService: OrderService,
    private cartService: CartService,
    private healthService: HealthReportService,
    private userService: UserService,
    private navigation: NavigationService,
    private scrollService: ScrollService
  ) {}

  /**
   * Gets the current product and resets the "Added to cart!" and "Out of stock!" messages
   */
  ngOnInit(): void {
    this.getProduct();
    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (!signedIn) return;
      this.getCurrentUser();
      this.doesUserHaveProductInCart();
    });

    this.getCurrentUser();
  }

  /**
   * Ask the server for the specified
   * product from the specified route id
   */
  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id).subscribe((product) => {
      if (!product) {
        this.navigation.navigate('/home');
        return;
      }
      this.product = product;

      // console.log(JSON.stringify(product, null, 2));

      if (this.product.quantity == 0) {
        this.outOfStock = true;
      }

      this.getHealthReports();
      this.doesUserHaveProductInCart();
      this.getAllSponsors();
      this.getSponsorshipDate();
    });
  }

  listenToCartChange() {
    this.cartService.cartSubject.subscribe(() => {});
  }

  /**
   * Ask the server to search for the
   * health reports of the specified falcon
   * and only if the product is a falocon with healthReports
   */
  getHealthReports() {
    if (this.product.productType != 1) return;
    this.healthService
      .searchHealthReports(this.product.id)
      .subscribe((reports: HealthReport[]) => {
        this.healthReports = reports;
      });
  }

  /**
   * Find all users then check to
   * see if any of them are apart
   * of this products sponsor list
   */
  getAllSponsors() {
    if (this.product.productType != 1) return;
    this.userService.getAllUsers().subscribe((allUsers) => {
      console.log('Found Users: ' + allUsers.length);
      for (let user of allUsers) {
        if (
          this.product.sponsors.filter((spon) => spon == user.username).length >
          0
        ) {
          this.sponsors.push(user);
        }
      }
    });
  }

  /**
   * Checks to see if the current user is signed in
   * @returns true if signed in, false otherwise
   */
  isUserSignedIn(): boolean {
    return this.userService.isSignedIn();
  }

  /**
   * Checks to see if the current user is an admin
   * @returns true if user is an admin, false otherwise
   */
  whoIsUser() {
    if (!this.user) return;

    this.isAdmin = this.user.type === UserType.Admin;

    this.isOrno = this.user.type === UserType.Ornithologist;
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

  /**
   * Set the current user variable
   */
  getCurrentUser(): void {
    if (!this.userService.isSignedIn()) return;

    this.user = this.userService.getCurrentUser();
    this.whoIsUser();
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

  /**
   * Exactly what it says
   */
  dateTimeToString(dateTime: Date) {
    let date = new Date(dateTime);
    return date.toLocaleDateString();
  }

  // navigate to the last route
  goBack() {
    this.closeModal();
    this.navigation.back();
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

  // navigation function
  toLogin() {
    this.closeModal();
    // this.router.navigate(['/sign_in']);
    this.navigation.navigate('login');
  }

  // navigate to the selected health report page
  toHealthReport(id: number) {
    this.closeModal();
    // this.router.navigate([`/health-report/${id}`]);
    this.navigation.navigate(`/report/${id}`);
  }

  // navigate to create a health report
  toCreateHealthReport() {
    this.closeModal();
    this.navigation.navigate(`/create/report/${this.product.id}`);
  }
}
