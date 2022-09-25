import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../types/Product';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  allProducts: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getAllInventory();
  }

  /**
   * Handles the behavior when the search is called
   */
  onSearchRequested(searchTerm: string): void {
    if(searchTerm.length == 0)
      this.getAllInventory(); 
    else
      this.searchProduct(searchTerm); 
  }

  /** 
   * Get all products (default behavior)
   */
  getAllInventory(): void {
    this.productService
      .getAllProducts()
      .subscribe((products) => (this.allProducts = products));
  }

  /**
   * Get products based on a search term.
   */
  searchProduct(searchTerm: string): void {
    this.productService
      .searchProducts(searchTerm)
      .subscribe((products) => (this.allProducts = products)); 
  }
}

