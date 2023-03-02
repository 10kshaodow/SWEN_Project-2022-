import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-manage-products',
  templateUrl: './manage-products.component.html',
  styleUrls: ['./manage-products.component.css'],
})
export class ManageProductsComponent implements OnInit {
  allProducts: Product[] = [];
  selected?: Product;
  errorMessage?: string;

  showForm: boolean = false;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getAllProducts();
  }

  deleteProduct(productId: number) {
    this.productService.deleteProduct(productId).subscribe((product) => {
      console.log(`Product(${product.id}) Deleted`);
      this.getAllProducts();
    });
  }

  getAllProducts() {
    this.allProducts = [];
    this.productService.getAllProducts().subscribe((products) => {
      this.allProducts = products;

      // for (let prod of products) {
      //   console.log(prod.fileName);
      // }
    });
  }

  selectProduct(product: Product) {
    this.selected = product;
    this.showForm = true;
    console.log(`Selected ${product.id}`);
  }

  toAllProducts() {
    this.showForm = false;
    this.selected = undefined;
    setTimeout(() => {
      // console.log(`ReLoading New Products`);
      this.getAllProducts();
    }, 2000);
  }

  setError(message?: string) {
    this.errorMessage = message;
  }

  toCreateProduct() {
    this.showForm = true;
  }
}
