import { Component, Input, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart/cart.service';
import { ProductService } from 'src/app/services/product/product.service';
import { OrderItem } from 'src/app/types/OrderItem';
import { Product } from 'src/app/types/Product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css'],
})
export class OrderItemComponent implements OnInit {
  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private router: Router
  ) {}
  @Input() routeTo!: string;
  @Input() orderItem!: OrderItem;
  @Input() viewOnly: boolean = false;
  @Input() showButtons: boolean = true;
  product?: Product;
  productNotFound: boolean = true;

  ngOnInit(): void {
    if (this.orderItem) {
      this.getProduct();
    } else {
      throw new TypeError();
    }
  }

  getProduct(): void {
    this.productService
      .getProduct(this.orderItem.productID)
      .subscribe((_product) => {
        if (_product) {
          this.product = _product;
          this.productNotFound = false;
        } else {
          this.product = this.productNotFoundPlaceholder();
        }
      });
  }

  removeFromCart(): void {
    this.cartService.removeFromCart(this.orderItem!.productID);
  }

  incrementQuantity(): void {
    if (this.orderItem && this.product) {
      let currentQuantity = this.orderItem.quantity;
      let newQuaunity: number = currentQuantity + 1;
      let maximumQuantity = this.product.quantity;
      if (newQuaunity <= maximumQuantity!) {
        this.cartService.updateOrderItemQuantity(
          this.orderItem.productID,
          newQuaunity
        );
      }
    }
  }

  decrementQuantity(): void {
    if (this.orderItem && this.product) {
      let currentQuantity = this.orderItem.quantity;
      let newQuaunity: number = currentQuantity - 1;
      if (newQuaunity > 0) {
        this.cartService.updateOrderItemQuantity(
          this.orderItem.productID,
          newQuaunity
        );
      } else if (newQuaunity <= 0) {
        this.cartService.removeFromCart(this.orderItem.productID);
      }
    }
  }

  productNotFoundPlaceholder(): Product {
    let id: number = -1;
    let name: String = 'Sorry, this product is no longer available';
    let price: number = 0;
    let productNotFound: Product = { id, name, price } as Product;

    if (!this.viewOnly) {
      this.orderItem.quantity = 0;
    }
    this.productNotFound = true;

    return productNotFound;
  }

  toProductDetail(): void {
    this.router.navigate([`/product-page/${this.orderItem.productID}`]);
  }
}
