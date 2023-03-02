import { Injectable } from '@angular/core';
import { Product } from 'src/app/types/Product';
import { ProductService } from 'src/app/services/product/product.service';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';
import { Observable, Subject } from 'rxjs';
import { Cart } from '../../types/Cart';
import { OrderItem } from 'src/app/types/OrderItem';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  currentUser!: User;
  productsList: Product[] = [];
  cartSubject: Subject<Cart> = new Subject<Cart>();
  shoppingCart!: Cart;
  TAX_RATE = 0.08;

  constructor(
    private productService: ProductService,
    private userService: UserService
  ) {
    this.getProducts();
    this.cartSubject.subscribe((_cart) => {
      this.shoppingCart = _cart;
    });
    this.cartSubject.next({ orderItemList: [] } as Cart);
  }

  private changeSubject(cart: Cart) {
    this.cartSubject.next(cart);
  }

  private getCurrentUser(): void {
    if (this.userService.isSignedIn()) {
      this.currentUser = this.userService.getCurrentUser();
    }
  }

  init(): void {
    this.getProducts();
  }

  getCart(): void {
    this.getCurrentUser();
    if (this.currentUser) {
      this.cartSubject.next(this.currentUser.cart);
    }
  }

  getProducts(): void {
    this.getCartProductsList().subscribe((products) => {
      this.productsList = products;
    });
  }

  getCartProductsList(): Observable<Product[]> {
    return this.productService.getAllProducts();
  }

  // repetitive?
  getProduct(id: number): Observable<Product> {
    return this.productService.getProduct(id);
  }

  getProductByID(id: number): Product {
    let productFound: Product | null = null;

    for (var product of this.productsList) {
      if (product.id === id) {
        productFound = product;
      }
    }

    return productFound!;
  }

  getOrderItemFromCart(id: number) {
    let index = this.findOrderItemInCart(id);

    if (index == -1) return;
    let orderItem = this.shoppingCart.orderItemList.at(index);
    return orderItem;
  }

  findOrderItemInCart(wantedProductID: number): number {
    let index = -1;

    if (this.shoppingCart) {
      for (let i = 0; i < this.shoppingCart.orderItemList.length; i++) {
        let itemProductID = this.shoppingCart.orderItemList[i].productID;
        if (itemProductID === wantedProductID) {
          index = i;
        }
      }
    }

    return index;
  }

  addToCart(newOrderItem: OrderItem): void {
    if (!this.currentUser) {
      this.getCart();
    }

    if (this.shoppingCart) {
      let index = this.findOrderItemInCart(newOrderItem.productID);
      if (index == -1) {
        this.shoppingCart.orderItemList.push(newOrderItem);
      } else {
        let product: Product | null = this.getProductByID(
          newOrderItem.productID
        );
        if (product) {
          if (product.productType == 1) {
          } else if (product.productType == 2) {
            this.shoppingCart.orderItemList[index].quantity +=
              newOrderItem.quantity;
          }
        }
      }

      this.save(this.shoppingCart);
    }
  }

  removeFromCart(productID: number) {
    let itemIndex: number = this.findOrderItemInCart(productID);
    if (itemIndex == -1) {
    } else {
      this.shoppingCart.orderItemList.splice(itemIndex, 1);
      this.save(this.shoppingCart);
    }
  }

  updateOrderItemQuantity(productID: number, quantity: number) {
    let itemIndex: number = this.findOrderItemInCart(productID);
    if (itemIndex == -1) {
    } else {
      this.shoppingCart.orderItemList[itemIndex].quantity = quantity;
      this.save(this.shoppingCart);
    }
  }

  private save(newCart: Cart): void {
    if (this.currentUser) {
      this.currentUser.cart = newCart;
      this.userService
        .updateUser(this.currentUser)
        .subscribe((updatedAcc: User) => {
          this.userService.setCurrentUser(updatedAcc);
          this.changeSubject(newCart);
        });
    }
  }

  calculateSubtotal(): number {
    let subtotal: number = 0;

    for (let i = 0; i < this.shoppingCart.orderItemList.length; i++) {
      let item: OrderItem = this.shoppingCart.orderItemList[i];
      let product: Product = this.getProductByID(item.productID);
      if (product) {
        subtotal += product.price * item.quantity;
      }
    }

    return subtotal;
  }

  calculateTaxes(): number {
    let taxes: number = this.calculateSubtotal() * this.TAX_RATE;
    return taxes;
  }

  calculateTotal(): number {
    return this.calculateSubtotal() + this.calculateTaxes();
  }
}
