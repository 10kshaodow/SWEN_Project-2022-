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

  getProduct(id: string): void {
    //const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService
      .getProduct(parseInt(id))
      .subscribe((product) => (this.product = product));
  }

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe((pro) => {
        console.log(`Product Updated ${pro.id}`);
      });
    }
  }
}
