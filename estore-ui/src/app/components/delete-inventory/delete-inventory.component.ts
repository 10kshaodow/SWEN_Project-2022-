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

  deleteInventory( id: number ) {
    this.productService.deleteProduct(id)
    
  }

}
