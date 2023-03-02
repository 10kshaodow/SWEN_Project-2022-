import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of } from 'rxjs';

import { Order } from 'src/app/types/Order';
import { OrderStatus } from 'src/app/types/OrderStatus';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private apiUrl = 'http://localhost:8080/orders';

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

    /**
   * Send a DELETE request to the api url
   * @param orderID of type number used apart of calling url
   *
   * @return Observable of the deleted Order
   */
     deleteOrder(orderID: number): Observable<Order> {
      let url = `${this.apiUrl}/${orderID}`;
      return this.http.delete<Order>(url, this.httpOptions).pipe(
        tap((item) => {
          console.log(`API deleted ${orderID} order`);
        }),
        catchError(this.handleError<Order>(`deleteOrder`))
      );
    }
  
    /**
     * Get all orders from the api
     *
     * @returns an Observable of order object array
     */
    getAllOrders(): Observable<Order[]> {
      return this.http.get<Order[]>(this.apiUrl).pipe(
        tap((array) => {
          console.log(`API returned ${array.length} orders`);
        }),
        catchError(this.handleError<Order[]>(`getAllOrders`, []))
      );
    }
  
    /**
     * GET a single order from the server
     *
     * @param id of type number used in the calling url
     *
     * @return Observable of the requested Order
     *  */
    getOrder(id: number): Observable<Order> {
      const url = `${this.apiUrl}/${id}`;
      return this.http.get<Order>(url).pipe(
        tap((_) => console.log(`fetched order with id of ${id}`)),
        catchError(this.handleError<Order>(`getOrder id=${id}`))
      );
    }
  
    /** PUT: update the order on the server
     * @param order
     * @return an observable allowing the caller to wait on receiving the updated order until the order update has completed
     */
    updateOrder(order: Order): Observable<any> {
      return this.http.put(this.apiUrl, order, this.httpOptions).pipe(
        tap((_) => console.log(`updated order id=${order.id}`)),
        catchError(this.handleError<any>('updateOrder'))
      );
    }
  
    /** POST: add a new order to the server
     * @param order - The new order created by the order based on custom data
     * @return an observable allowing the caller to wait on receiving the newly created order until the order creation has completed
     */
    addOrder(order: Order): Observable<Order> {
      //if(this.checkOrderExists(order.id))
        //throw new OrderExistsError(`the id ${order.id} is already taken. Order ids must be unique`); 
        console.log("Adding order: " + JSON.stringify(order));
        return this.http.post<Order>(this.apiUrl, order, this.httpOptions).pipe(
        tap((newOrder: Order) =>
          console.log(`added order w/ id=${newOrder.id}`)
        ),
        catchError(this.handleError<Order>('addOrder'))
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
