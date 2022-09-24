import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of } from 'rxjs';

import { Product } from '../types/Product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}

  /*
    Ask the server for all of the products

    returns an observable of product object array
  */
  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl).pipe(
      tap((array) => {
        console.log(`API returned ${array.length} products`);
      }),
      catchError(this.handleError<Product[]>(`getAllProducts`, []))
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
