import { Component, OnInit } from '@angular/core';
import {
  debounceTime,
  distinctUntilChanged,
  Observable,
  Subject,
  switchMap,
} from 'rxjs';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  allProducts$!: Observable<Product[]>;
  filtered$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.allProducts$ = this.getAllProducts();

    this.filtered$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.productService.searchProducts(term))
    );
  }

  /**
   *  Add the inputed term to the subject of searchTerms
   */
  searchTerm(term: string) {
    this.searchTerms.next(term);
  }

  /**
   * Communicate with the Service to
   * get and then set all the products
   * on the very first load
   */
  getAllProducts() {
    return this.productService.getAllProducts();
  }
}
