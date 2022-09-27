import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product/product.service';
import { Product } from '../../types/Product';

@Component({
  selector: 'app-delete-inventory',
  templateUrl: './delete-inventory.component.html',
  styleUrls: ['./delete-inventory.component.css']
})
export class DeleteInventoryComponent implements OnInit {


  constructor(private productService: ProductService) {}

  ngOnInit(): void {
   
  }
/**
   * uses the id string the user inputs, and converts the string id 
   * into an int such that the deleteProduct can be performed using the id. 
   * removes  a single product from the product list
   *
   * @remarks
   * This method is part of the {@link DeleteInventoryComponent | Components}.
   *
   * @param id - The first input string
   *
   */
  deleteInventory( id: string ) {
    console.log("deleteing...")
    this.productService.deleteProduct(parseInt(id)).subscribe()
  }

}
