import { Component, OnInit } from '@angular/core';

import { Product } from '../../types/Product';
import { ProductService } from '../../services/product/product.service';

@Component({
  selector: 'app-get-product',
  templateUrl: './get-product.component.html',
  styleUrls: ['./get-product.component.css']
})
export class GetProductComponent implements OnInit {
  product?: Product;

  constructor(
    private productService: ProductService
  ) { }

  ngOnInit(): void {

  }

  /**
   * Makes a server request for a single product.
   * 
   * @param id - The id number of the product to be retrieved
   */
  getProduct(id: string): void{
    const productID = parseInt(id, 10);
    this.productService.getProduct(productID).subscribe(product => this.product = product);
  }

}
