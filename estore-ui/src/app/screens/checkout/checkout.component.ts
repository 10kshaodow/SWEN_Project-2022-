import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { CartService } from 'src/app/services/cart/cart.service';
import { User } from 'src/app/types/User';
import { OrderStatus } from 'src/app/types/OrderStatus';
import { Address } from 'src/app/types/Address';
import { CreditCard } from 'src/app/types/CreditCard';
import { OrderItem } from 'src/app/types/OrderItem';
import { Cart } from 'src/app/types/Cart';
import { Order } from 'src/app/types/Order';
import { OrderService } from 'src/app/services/order/order.service';
import { UserType } from 'src/app/types/UserType';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { Notification } from 'src/app/types/Notification';
import { Router } from '@angular/router';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css'],
})
export class CheckoutComponent implements OnInit {
  constructor(
    private userService: UserService,
    private cartService: CartService,
    private orderService: OrderService,
    private productService: ProductService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  user!: User;
  cart!: Cart;
  order!: Order;
  products: Product[] = [];
  errorMessage: string = '';
  displayUserConfirmation: Boolean = true;
  displayReviewOrder: Boolean = false;
  displayOrderConfirmation: boolean = false;
  displayOrderFailure: boolean = false;
  displayErrorMessage: boolean = false;

  private readonly USER_FIELDS_LEFT_BLANK: string =
    'You may not leave any address or payment info blank';

  ngOnInit(): void {
    this.displayUserConfirmation = true;
    this.displayReviewOrder = false;
    this.displayOrderConfirmation = false;
    this.displayOrderFailure = false;
    this.displayErrorMessage = false;

    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (!signedIn) return;
      this.getCurrentUser();
      if (this.user) {
        this.getCart();
      }

      this.getProducts();
    });

    this.getCurrentUser();
    if (this.user) {
      this.getCart();
    }

    this.getProducts();
  }

  getCurrentUser(): void {
    if (this.userService.isSignedIn()) {
      this.user = this.userService.getCurrentUser();
    }
  }

  getCart(): void {
    console.log('Were getting ' + this.user.name + ' cart');
    this.cartService.cartSubject.subscribe((cart_) => {
      this.cart = cart_;
      console.log('We have the cart!' + this.cart.orderItemList.length);
    });
    this.cartService.getCart();
  }

  getProducts(): void {
    this.productService.getAllProducts().subscribe((_products) => {
      this.products = _products;
    });
  }

  getProductByID(id: number): Product | null {
    if (this.products.length > 0) {
      for (let i = 0; i < this.products.length; i++) {
        if (this.products[i].id == id) {
          return this.products[i];
        }
      }
    }

    return null;
  }

  userDetailsConfirmed(): void {
    this.getCurrentUser();

    if (this.verifyUserDetails()) {
      this.displayUserConfirmation = false;
      this.displayErrorMessage = false;
      this.displayReviewOrder = true;
      console.log("The user has confirmed their details. proceeding to checkout")
      this.createOrder();
    } else {
      this.errorMessage = this.USER_FIELDS_LEFT_BLANK;
      this.displayErrorMessage = true;
    }
  }

  /**
   * Checks if user's username, address, and payment info are acceptble
   * @returns true when all required fields are filled in
   */
  verifyUserDetails(): boolean {
    if (this.user) {
      if (
        this.user.username.length > 0 &&
        this.verifyAddress() &&
        this.verifyPaymentInfo()
      ) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if user's address info is acceptble
   * @returns true when all required fields are filled in
   */
  verifyAddress(): boolean {
    if (this.user) {
      if (
        this.user.address.street.length > 0 &&
        this.user.address.city.length > 0 &&
        this.user.address.state.length > 0 &&
        this.user.address.zipcode.length > 0 &&
        this.user.address.country.length > 0
      ) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if user's payment info is acceptble
   * @returns true when all required fields are filled in
   */
  verifyPaymentInfo(): boolean {
    if (this.user) {
      if (
        this.user.creditCard.number.length > 0 &&
        this.user.creditCard.expirationMonth.length > 0 &&
        this.user.creditCard.expirationYear.length > 0 &&
        this.user.creditCard.cvv.length > 0
      ) {
        return true;
      }
    }

    return false;
  }

  createOrder(): void {
    console.log("We are creating the order")
    let username: string = this.user.username;
    let orderDate: Date = new Date();
    let status: OrderStatus = OrderStatus.UnderReview;
    let address: Address = this.user.address;
    let creditCard: CreditCard = this.user.creditCard;
    let orderItemList: OrderItem[] = this.cart.orderItemList;
    let subtotal = this.cartService.calculateSubtotal();
    let taxes = this.cartService.calculateTaxes();
    let total = this.cartService.calculateTotal();

    this.order = {
      username,
      orderDate,
      status,
      address,
      creditCard,
      orderItemList,
      subtotal,
      taxes,
      total,
    } as Order;
    this.updatePrices();
  }

  backToConfirmUserDetails(): void {
    this.displayUserConfirmation = true;
    this.displayReviewOrder = false;
  }

  placeOrder(): void {
    console.log("placing order");
    if (this.order) {
      this.updateOrderBeforeSubmission();
      this.orderService.addOrder(this.order).subscribe((_order) => {
        if (_order) {
          this.order = _order;
          this.displayReviewOrder = false;
          this.displayOrderConfirmation = true;
          this.updateUserAfterOrder();
          this.updateInventoryAfterOrder();
        } else {
          this.displayOrderFailure = true;
        }
      });
    }
  }

  updateOrderBeforeSubmission(): void {
    this.order.status = OrderStatus.Ordered;
  }

  updatePrices(): void {
    for (let i = 0; i < this.order.orderItemList.length; i++) {
      let product = this.getProductByID(this.order.orderItemList[i].productID);
      if (product) {
        this.order.orderItemList[i].price = product.price;
      }
    }
  }

  updateUserAfterOrder(): void {
    if (this.order) {
      this.user.orderHistory.push(this.order.id.toString());
      this.user.cart = { orderItemList: [] } as Cart;
      this.userService.updateUser(this.user).subscribe((_user) => {
        console.log(
          'User after order update' + JSON.stringify(this.user, null, 2)
        );
      });
    }
  }

  updateInventoryAfterOrder() {
    for (var orderItem of this.order.orderItemList) {
      let productID = orderItem.productID;
      let boughtQuantity = orderItem.quantity;
      this.productService.getProduct(productID).subscribe((_product) => {
        if (_product.productType == 1) {
          _product.sponsors.push(this.user.username);
          console.log("Adding " + this.order.username + " To sponsors: " + _product.sponsors);
          this.notifySponsors(_product.name, _product.sponsors);

        } else if (_product.productType == 2) {
          let originalQuantity = _product.quantity;
          let updateQuantity = originalQuantity - boughtQuantity;
          _product.quantity = updateQuantity;
        }

        this.productService.updateProduct(_product).subscribe((__product) => {
          console.log("productUpdated");
        });
      });
    }
  }

  notifySponsors(name: string, sponsors: string[]): void{
    let title: string = name + " has a new sponsor!";
    let message: string = this.order.username + " is now sponsoring " + name;
    let sponsorNotification: Notification = {title, message} as Notification;

    this.notificationService.addNotification(sponsorNotification).subscribe((notification) => {
      for(let i = 0; i < sponsors.length; i++){
        let sponsorID = sponsors[i];
        //if(sponsorID !== this.order.username){
          this.userService.getUser(sponsorID).subscribe((user)=>{
            this.notificationService.addNotificationToUsers(notification, [user]);
          })
        //}
      }
    })
  }

  isUserSignedIn(): boolean {
    return this.userService.isSignedIn();
  }

  isUserCustomer(): boolean {
    if (this.userService.isSignedIn()) {
      return this.userService.getCurrentUser().type == UserType.Customer;
    }

    return false;
  }

  toCart() {
    this.router.navigate(['/cart']);
  }
}
