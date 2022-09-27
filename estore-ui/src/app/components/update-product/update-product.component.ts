import { Component, OnInit, Input } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../types/Product';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css'],
})
export class UpdateProductComponent implements OnInit {
  product?: Product;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {}

  /*
  * Obtains a product based on the ID provided
  * @param id - the indentification number of the product
  */
  getProduct(id: string): void {
    this.productService
      .getProduct(parseInt(id))
      .subscribe((product) => (this.product = product));
  }

  /**
   * Saves changes that a user makes to a product
   */
  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe((pro) => {
        console.log(`Product Updated ${pro.id}`);
      });
    }
  }

}
