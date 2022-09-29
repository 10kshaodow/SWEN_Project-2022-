import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  allProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getAllProducts();
  }

  /**
   * Communicate with the Service to
   * get and then set all the products
   * on the very first load
   */
  getAllProducts() {
    this.productService.getAllProducts().subscribe((products) => {
      this.allProducts = products;
    });
  }
}
