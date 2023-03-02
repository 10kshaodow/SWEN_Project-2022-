import { Component, OnInit } from '@angular/core';

import { ProductService } from 'src/app/services/product/product.service';

import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';

import { Cart } from 'src/app/types/Cart';
import { OrderItem } from 'src/app/types/OrderItem';
import { CartService } from 'src/app/services/cart/cart.service';
import { UserType } from 'src/app/types/UserType';

import { ScrollService } from 'src/app/services/scroll/scroll.service';
import { NavigationService } from 'src/app/services/navigation/navigation.service';
import { OrderStatus } from 'src/app/types/OrderStatus';
import { Notification } from 'src/app/types/Notification';
import { Address } from 'src/app/types/Address';
import { CreditCard } from 'src/app/types/CreditCard';
import { Order } from 'src/app/types/Order';
import { Product } from 'src/app/types/Product';
import { OrderService } from 'src/app/services/order/order.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  user?: User;
  cart: Cart = { orderItemList: [] } as Cart;

  allProducts: Product[] = [];
  finalOrder?: Order;

  showModal: boolean = false;
  errorMessage?: string;
  displayErrorMessage: boolean = false;

  constructor(
    private userService: UserService,
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private notificationService: NotificationService,
    private navigation: NavigationService,
    private scrollService: ScrollService
  ) {
    this.cartService.cartSubject.subscribe((cart_) => {
      this.cart = cart_;
      console.log('Subscibe! ' + JSON.stringify(this.cart));
    });
  }

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((_signedIn) => {
      if (!_signedIn) return;
      this.getCart();
      this.setUser();
      this.getProducts();
    });

    this.getCart();
    this.setUser();
    this.getProducts();
  }

  getCart(): void {
    this.cartService.getCart();
    console.log(
      'getCart in cart component: ' +
        JSON.stringify(this.cart, null, 2) +
        ' ---- ' +
        this.cart.orderItemList.length
    );
  }

  addToCart(newOrderItem: OrderItem): void {
    this.cartService.addToCart(newOrderItem);
  }

  removeFromCart(productID: number): void {
    this.cartService.removeFromCart(productID);
  }

  updateCart(productID: number, quantity: number) {
    this.cartService.updateOrderItemQuantity(productID, quantity);
  }

  calculateSubtotal(): number {
    return this.cartService.calculateSubtotal();
  }

  calculateTaxes(): number {
    return this.cartService.calculateTaxes();
  }

  calculateTotal(): number {
    return this.cartService.calculateTotal();
  }

  isUserSignedIn(): boolean {
    return this.userService.isSignedIn();
  }

  setUser() {
    this.user = this.userService.getCurrentUser();
  }

  getProducts(): void {
    this.productService.getAllProducts().subscribe((_products) => {
      this.allProducts = _products;
    });
  }

  getProductByID(id: number): Product | null {
    if (this.allProducts.length > 0) {
      for (let i = 0; i < this.allProducts.length; i++) {
        if (this.allProducts[i].id == id) {
          return this.allProducts[i];
        }
      }
    }

    return null;
  }

  isUserCustomer(): boolean {
    return this.user?.type === UserType.Customer;
  }

  isAddressFilled() {
    if (!this.user) return false;

    if (!this.user.address) return false;

    let addy = this.user.address;

    if (addy.city.length == 0) return false;
    if (addy.street.length == 0) return false;
    if (addy.state.length == 0) return false;
    if (addy.country.length == 0) return false;
    if (addy.zipcode.length == 0) return false;

    return true;
  }

  isCardFilled() {
    if (!this.user) return false;

    if (!this.user.address) return false;

    let card = this.user.creditCard;

    if (card.cvv.length === 0) return false;
    if (card.expirationMonth.length === 0) return false;
    if (card.expirationYear.length === 0) return false;
    if (card.number.length === 0) return false;

    return true;
  }

  isUserValid() {
    if (!this.user) return false;

    if (this.user.name.length == 0) return false;

    if (!this.isAddressFilled()) return false;

    if (!this.isCardFilled()) return false;

    if (!this.isUserCustomer()) return false;

    return true;
  }

  verifyCart(): void {
    if (this.cart.orderItemList.length == 0) {
      this.errorMessage = 'You must have items in your cart';
      return;
    }

    console.log('Running order verification;');

    let productsNotAvailable: boolean = false;
    let productsReturned = 0;

    for (let orderItem of this.cart.orderItemList) {
      this.productService
        .getProduct(orderItem.productID)
        .subscribe((_product) => {
          productsReturned++;

          if (!_product) {
            this.errorMessage =
              'Please remove unavailable items from your cart before you checkout';
            productsNotAvailable = true;
            return;
          }

          if (orderItem.quantity > _product.quantity) {
            // console.log('We found an unavailable product');
            this.errorMessage =
              'You have ' +
              orderItem.quantity +
              ' of product ' +
              orderItem.productID +
              ' in your cart, but only ' +
              _product.quantity +
              ' are available.';

            productsNotAvailable = true;
            return;
          }

          if (productsNotAvailable) {
            return;
          }

          if (
            productsReturned == this.cart.orderItemList.length &&
            !productsNotAvailable
          ) {
            this.errorMessage = undefined;
            this.submitOrder();
          }
        });
    }
  }

  setModal(change: boolean) {
    this.showModal = change;
    // change is true then scrolling needs to be canceled
    this.scrollService.setScroll(!change);
  }

  toHome() {
    this.setModal(false);
    this.navigation.navigate('/home');
  }

  toLogin() {
    this.setModal(false);
    this.navigation.navigate('/login');
  }

  submitOrder() {
    let order = this.createOrder();
    this.orderService.addOrder(order).subscribe((_order) => {
      if (_order) {
        this.finalOrder = _order;
        this.updateUserAfterOrder();
        this.updateInventoryAfterOrder();
        this.setModal(true);
      } else {
        this.errorMessage = 'Sorry, we failed to place your order';
      }
    });
  }

  updateUserAfterOrder(): void {
    if (!this.finalOrder || !this.user) return;

    this.user.orderHistory.push(this.finalOrder.id.toString());
    this.user.cart = { orderItemList: [] } as Cart;
    this.userService.updateUser(this.user).subscribe((_user) => {
      console.log(
        'User after order update' + JSON.stringify(this.user, null, 2)
      );
    });
  }

  updateInventoryAfterOrder() {
    if (!this.finalOrder || !this.user) return;

    for (var orderItem of this.finalOrder.orderItemList) {
      let productID = orderItem.productID;
      let boughtQuantity = orderItem.quantity;
      this.productService.getProduct(productID).subscribe((_product) => {
        if (_product.productType == 1) {
          _product.sponsors.push(this.user!.username);
          console.log(
            'Adding ' +
              this.finalOrder!.username +
              ' To sponsors: ' +
              _product.sponsors
          );
          this.notifySponsors(_product.name, _product.sponsors);
        } else if (_product.productType == 2) {
          let originalQuantity = _product.quantity;
          let updateQuantity = originalQuantity - boughtQuantity;
          _product.quantity = updateQuantity;
        }

        this.productService.updateProduct(_product).subscribe((__product) => {
          console.log('productUpdated');
        });
      });
    }
  }

  notifySponsors(name: string, sponsors: string[]): void {
    if (!this.finalOrder || !this.user) return;

    let title: string = name + ' has a new sponsor!';
    let message: string =
      this.finalOrder.username + ' is now sponsoring ' + name;
    let sponsorNotification = { title, message } as Notification;

    this.notificationService
      .addNotification(sponsorNotification)
      .subscribe((notification) => {
        for (let i = 0; i < sponsors.length; i++) {
          let sponsorID = sponsors[i];
          //if(sponsorID !== this.order.username){
          this.userService.getUser(sponsorID).subscribe((user) => {
            this.notificationService.addNotificationToUsers(notification, [
              user,
            ]);
          });
          //}
        }
      });
  }

  updatePrices(order: Order) {
    for (let i = 0; i < order.orderItemList.length; i++) {
      let product = this.getProductByID(order.orderItemList[i].productID);
      if (product) {
        order.orderItemList[i].price = product.price;
      }
    }
    return order;
  }

  createOrder() {
    console.log('We are creating the order');
    let username: string = this.user!.username;
    let orderDate: Date = new Date();
    let status: OrderStatus = OrderStatus.Ordered;
    let address: Address = this.user!.address;
    let creditCard: CreditCard = this.user!.creditCard;
    let orderItemList: OrderItem[] = this.cart.orderItemList;
    let subtotal = this.cartService.calculateSubtotal();
    let taxes = this.cartService.calculateTaxes();
    let total = this.cartService.calculateTotal();

    // orderItemList = this.updatePrices(orderItemList)

    let order = {
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

    order = this.updatePrices(order);

    return order;
  }

  // toCheckout(): void {
  //   this.navigation.navigate(['/checkout']);
  // }
}
