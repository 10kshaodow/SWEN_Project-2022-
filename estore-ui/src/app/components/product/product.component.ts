import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  // Product Component Must be called with a product passed into it
  @Input() product!: Product;

  constructor() {}

  ngOnInit(): void {}
}
