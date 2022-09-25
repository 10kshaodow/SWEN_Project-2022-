import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../../types/Product';
import { ProductService } from '../../services/product/product.service';

@Component({
  selector: 'app-get-product',
  templateUrl: './get-product.component.html',
  styleUrls: ['./get-product.component.css']
})
export class GetProductComponent implements OnInit {
  product: Product | undefined;

  constructor(
    private productService: ProductService, 
    private route: ActivatedRoute, 
    private location: Location
  ) { }

  ngOnInit(): void {
    
  }

  getProduct(id: string): void{
    const productID = parseInt(id, 10);
    this.productService.getProduct(productID).subscribe(product => this.product = product);
  }

  goBack(): void{
    this.location.back();
  }

}
