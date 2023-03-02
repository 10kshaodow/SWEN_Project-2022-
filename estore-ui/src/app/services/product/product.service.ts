import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of } from 'rxjs';

import { Product } from '../../types/Product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  /**
   * Send a DELETE request to the api url
   * @param productID of type number used apart of calling url
   *
   * @return Observable of the deleted Product
   */
  deleteProduct(productID: number): Observable<Product> {
    let url = `${this.apiUrl}/${productID}`;
    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap((item) => {
        console.log(`API deleted ${productID} product`);
      }),
      catchError(this.handleError<Product>(`deleteProduct`))
    );
  }

  /**
   * Get all products from the api
   *
   * @returns an Observable of product object array
   */
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl).pipe(
      tap((array) => {
        console.log(`API returned ${array.length} products`);
      }),
      catchError(this.handleError<Product[]>(`getAllProducts`, []))
    );
  }

  /**
   * GET a single product from the server
   *
   * @param id of type number used in the calling url
   *
   * @return Observable of the requested Product
   *  */
  getProduct(id: number): Observable<Product> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Product>(url).pipe(
      tap((_) => console.log(`fetched product with id of ${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /**
   * GET heroes whose name contains search term
   *
   * @param term type of string used in the calling url
   *
   * @return Observable of an Array of Products that match the terms
   *  */
  searchProducts(term: string): Observable<Product[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Product[]>(`${this.apiUrl}/?searchTerm=${term}`).pipe(
      tap((x) =>
        x.length
          ? console.log(`found products matching "${term}"`)
          : console.log(`no products matching "${term}"`)
      ),
      catchError(this.handleError<Product[]>('searchHeroes', []))
    );
  }

  /** PUT: update the product on the server
   * @param product
   * @return an observable allowing the caller to wait on receiving the updated product until the product update has completed
   */
  updateProduct(product: Product): Observable<any> {
    console.log("Updated product: " + JSON.stringify(product, null, 2));
    return this.http.put(this.apiUrl, product, this.httpOptions).pipe(
      tap((_) => console.log(`updated product id=${product.id}`)),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /** POST: add a new product to the server
   * @param product - The new product created by the user based on custom data
   * @return an observable allowing the caller to wait on receiving the newly created product until the product creation has completed
   */
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product, this.httpOptions).pipe(
      tap((newProduct: Product) =>
        console.log(`\nProduct Created w/ id=${newProduct.id}\n`)
      ),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /*
    Simple Error handler will log it to browser console

    returns an observable of specified type
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log();
      console.log(`${operation} failed`);
      return of(result as T);
    };
  }
}
