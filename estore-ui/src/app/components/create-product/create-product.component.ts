import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../types/Product';


@Component({
  selector: 'app-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.css']
})
export class CreateProductComponent implements OnInit {

  constructor(private productService: ProductService) { }

  products: Product[] = [];

  ngOnInit(): void {
   this.getProducts();
 }
 
 getProducts(): void {
   this.productService.getAllProducts()
       .subscribe(products => this.products = products);
 }
 
   selectedProduct?: Product;
   onSelect(product: Product): void {
     this.selectedProduct = product;
   }
 
   create(price_val: string, name: string, description: string): void {
     name = name.trim();
     var price: number = parseInt(price_val);
     if (!name) { return; }
     this.productService.addProduct({name, price, description} as Product)
       .subscribe(product => {
         this.getProducts();
       });

   }

}
