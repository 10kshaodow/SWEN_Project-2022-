import { Component, OnInit } from '@angular/core';

import {
  debounceTime,
  distinctUntilChanged,
  Observable,
  of,
  Subject,
  switchMap,
} from 'rxjs';
import { ProductService } from 'src/app/services/product/product.service';
import { UserService } from 'src/app/services/user/user.service';
import { Product } from 'src/app/types/Product';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  isOrno: boolean = false;
  allProducts$!: Observable<Product[]>;
  falcons: Product[] = [];
  accessories: Product[] = [];

  filtered$!: Observable<Product[]>;
  filterProducts: Product[] = [];
  filterAccessories: Product[] = [];
  private searchTerms = new Subject<string>();
  searching?: string;
  isSearching: boolean = false;
  searchFound: boolean = false;

  constructor(
    private productService: ProductService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.allProducts$ = this.getAllProducts();
    this.categorizeProducts();

    this.userService.userCurrentlySignedIn.subscribe(() => {
      this.whoIsUser();
    });

    this.whoIsUser();

    this.filtered$ = this.searchTerms.pipe(
      debounceTime(100),
      distinctUntilChanged(),
      switchMap((term: string) => {
        this.productService.searchProducts(term).subscribe((prods) => {
          console.log('Found: ' + prods.length);
          if (prods.length > 0) {
            this.searchFound = true;
          } else {
            this.searchFound = false;
          }
        });
        return this.productService.searchProducts(term);
      })
    );

    this.filtered$.subscribe((filteredProducts) => {
      this.filterAccessories = [];
      this.filterProducts = [];
      for (let prod of filteredProducts) {
        if (prod.productType == 1) {
          this.filterProducts.push(prod);
        } else {
          this.filterAccessories.push(prod);
        }
      }
    });
  }

  /**
   *  Add the inputed term to the subject of searchTerms
   */
  searchTerm(event: Event) {
    let input = event.target as HTMLInputElement;
    let term = input.value;
    if (term.length > 0) {
      this.isSearching = true;
    } else {
      this.isSearching = false;
    }
    this.searching = term;
    this.searchTerms.next(term);
  }

  whoIsUser() {
    this.isOrno =
      this.userService.getCurrentUser().type === UserType.Ornithologist;
  }

  /**
   * Communicate with the Service to
   * get and then set all the products
   * on the very first load
   */
  getAllProducts() {
    return this.productService.getAllProducts();
  }

  categorizeProducts(): void {
    this.productService.getAllProducts().subscribe((_products) => {
      _products.forEach((product) => {
        if (product.productType == 1) {
          this.falcons.push(product);
        } else if (product.productType == 2) {
          this.accessories.push(product);
        }
      });
    });
  }
}
