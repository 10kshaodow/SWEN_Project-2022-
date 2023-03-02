import { Component, OnInit } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { OrderService } from 'src/app/services/order/order.service';
import { UserService } from 'src/app/services/user/user.service';
import { Address } from 'src/app/types/Address';
import { Cart } from 'src/app/types/Cart';
import { CreditCard } from 'src/app/types/CreditCard';
import { Order } from 'src/app/types/Order';
import { OrderItem } from 'src/app/types/OrderItem';

import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-user-sign-in',
  templateUrl: './user-sign-in.component.html',
  styleUrls: ['./user-sign-in.component.css'],
})
export class UserSignInComponent implements OnInit {
  // error strings
  private readonly USERNAME_NOT_FOUND: string =
    'We could not find an account with that username.';
  private readonly USERNAME_TAKEN: string =
    'That username is already taken, please try a different one.';
  private readonly USERNAME_EMPTY: string = 'Your username cannot be blank';
  private readonly USERNAME_HAS_WHITESPACE: string =
    'Your username may not contain whitespace';

  showSignInScreen: boolean = false;
  showCreateAccountScreen: boolean = false;
  signedIn: boolean = false;
  currentUser?: User = undefined;
  orders: Order[] = [];

  showImmutableUsernameMessage: boolean = false;

  // username: string = '';

  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((_signedIn) => {
      console.log('Subscribed: ' + _signedIn);
      if (_signedIn) {
        this.userSignedIn();
      } else {
        this.showSignIn();
      }
    });

    if (this.userService.isSignedIn()) {
      this.userSignedIn();
    } else {
      this.showSignIn();
    }
  }

  /**
   * sets the view of the screen to user details
   */
  userSignedIn(): void {
    if (!this.userService.isSignedIn()) {
      this.currentUser = undefined;
      this.showSignIn();
      return;
    }
    this.currentUser = this.userService.getCurrentUser();
    this.showCreateAccountScreen = false;
    this.showSignInScreen = false;
    this.signedIn = true;
    this.getUserOrders();
  }

  /**
   * sets the view of the screen to the sign in screen
   */
  showSignIn(): void {
    this.clearError();

    this.showCreateAccountScreen = false;
    this.signedIn = false;
    this.showSignInScreen = true;
  }

  /**
   * sets the view of the screen to creating an account
   */
  showCreateAccount(): void {
    this.clearError();

    this.showSignInScreen = false;
    this.signedIn = false;
    this.showCreateAccountScreen = true;
  }

  /**
   * checks if the format of a username is acceptable
   *
   * @returns true when username format is acceptable
   */
  verifyUsernameFormat(username: string): boolean {
    // trim whitespace off and check if username is empty
    username = username.trim();
    if (username.length == 0) {
      this.errorMessage = this.USERNAME_EMPTY;
      return false;
    }

    // check for whitespace
    if (/\s/.test(username)) {
      this.errorMessage = this.USERNAME_HAS_WHITESPACE;
      return false;
    }

    return true;
  }

  /**
   * attempts to sign in a user
   */
  signIn(username: string): void {
    this.clearError();

    // do nothing if the user is signed on
    if (this.userService.isSignedIn()) {
      this.userSignedIn();
      return;
    }

    // verify the username is correctly formatted
    if (!this.verifyUsernameFormat(username)) {
      return;
    }

    // check if the username exists, and sign in if it does
    this.userService.checkUsernameExists(username).subscribe((exists) => {
      if (exists) {
        this.userService.getUser(username).subscribe((user) => {
          this.userService.setCurrentUser(user);
          // show the user information when they are signed in
          if (this.userService.isSignedIn()) {
            this.userSignedIn();
            return;
          }
        });
      } else {
        this.errorMessage = this.USERNAME_NOT_FOUND;
      }
    });
  }

  /**
   * sign out a user
   */
  signOut(): void {
    this.clearError();

    // don't do anything if a user is not signed in
    if (!this.userService.isSignedIn()) {
      return;
    }

    // sign out the user
    this.userService.signOut();
    this.showSignIn();
  }

  /**
   * attempts to save user info to backend
   */
  saveUser(): void {
    this.clearError();

    if (this.signedIn) {
      this.userService.updateUser(this.currentUser!).subscribe((user) => {
        console.log(
          'Updated user in sign in' + JSON.stringify(this.currentUser, null, 2)
        );
      });
    }
  }

  /**
   * Set the full current user object
   */
  setUser(changed: User) {
    this.currentUser = changed;
  }

  /**
   * attempts to create a new user from a username
   */
  createUser(username: string): void {
    this.clearError();

    // do nothing if the user is signed on
    if (this.userService.isSignedIn()) {
      this.userSignedIn();
      return;
    }

    // verify the username is correctly formatted
    if (!this.verifyUsernameFormat(username)) {
      return;
    }

    // check if the username exists, and create an account if it isn't taken
    this.userService.checkUsernameExists(username).subscribe((exists) => {
      if (exists) {
        console.log('username already exists');
        this.errorMessage = this.USERNAME_TAKEN;
      } else {
        let newAddress: Address = {
          street: '',
          city: '',
          state: '',
          country: '',
          zipcode: '',
        };
        let newCreditCard: CreditCard = {
          number: '',
          expirationMonth: '',
          expirationYear: '',
          cvv: '',
        };

        let newOrderItemList: OrderItem[] = [];
        let newCart: Cart = { orderItemList: newOrderItemList };

        let newUser: User = {
          username: username,
          name: '',
          type: UserType.Customer,
          address: newAddress,
          creditCard: newCreditCard,
          orderHistory: [],
          cart: newCart,
          notifications: [],
          notificationHistory: [],
        };

        this.userService.addUser(newUser).subscribe((user) => {
          this.userService.setCurrentUser(newUser);
          this.currentUser = this.userService.getCurrentUser();
          this.userSignedIn();
          console.log(JSON.stringify('inside signin: ' + JSON.stringify(user)));
        });
      }
    });
  }

  /**
   * Check if the current user is a customer
   */
  isUserCustomer(): boolean {
    if (this.userService.isSignedIn()) {
      return this.userService.getCurrentUser().type == UserType.Customer;
    }

    return false;
  }

  /**
   * Clear the error message
   */
  clearError() {
    this.errorMessage = '';
  }

  /**
   * Get all the orders and save orders that belong to the current user
   */
  getUserOrders() {
    if (!this.currentUser) return;

    this.orderService.getAllOrders().subscribe((_orders) => {
      this.orders = [];
      for (let order of _orders) {
        if (order.username == this.currentUser?.username) {
          this.orders.push(order);
        }
      }
    });
  }
}
