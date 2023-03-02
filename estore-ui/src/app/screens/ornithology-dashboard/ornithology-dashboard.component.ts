import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-ornithology-dashboard',
  templateUrl: './ornithology-dashboard.component.html',
  styleUrls: ['./ornithology-dashboard.component.css']
})
export class OrnithologyDashboardComponent implements OnInit {

  constructor(private productService: ProductService) { }
  falcons: Product[] = [];

  ngOnInit(): void {
    this.getAllFalcons();
  }


  getAllFalcons(): void{
    this.productService.getAllProducts().subscribe((_products) => {
      _products.forEach((_product) => {
        if(_product.productType == 1){
          this.falcons.push(_product);
        }
      });
    })
  }
}
