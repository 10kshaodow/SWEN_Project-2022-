import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../types/Product';

@Component({
  selector: 'app-all-inventory',
  templateUrl: './all-inventory.component.html',
  styleUrls: ['./all-inventory.component.css'],
})
export class AllInventoryComponent implements OnInit {
  allProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getAllInventory();
  }

  /*
    Ask the service to call the server to display the products
  */
  getAllInventory() {
    this.productService
      .getAllProducts()
      .subscribe((products) => (this.allProducts = products));
  }
}
