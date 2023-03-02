import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of, observable, subscribeOn } from 'rxjs';

import { Notification } from 'src/app/types/Notification';
import { UserService } from '../user/user.service';
import { User } from 'src/app/types/User';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
// import sockjs from 'sockjs-client/dist/sockjs';
// import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  socket = new SockJS('http://localhost:8080/sba-websocket');
  stompClient = Stomp.over(this.socket);
  private apiUrl = 'http://localhost:8080/notifications';
  notificationUpdates: Subject<string> = new Subject<string>();

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient, private userService: UserService) {
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
    if (this.stompClient.connected) {
      this.stompClient.subscribe('/topic', (message: string) => {
        this.notificationUpdates.next(message);
      });
    } else {
      this.stompClient.connect({}, (): any => {
        this.stompClient.subscribe('/topic', (message: string) => {
          this.notificationUpdates.next(message);
        });
      });
    }
  }

  // sendMessage(){
  //   this.stompClient.send("/app/send/message" , {}, null);
  //   $('#input').val('');
  // }

  /**
   * adds a notification to the specified users
   * @param notification the notification to add
   * @param users the users to add the notification to
   */
  addNotificationToUsers(notification: Notification, users: User[]): void {
    users.forEach((user) => {
      this.userService
        .addNotificationToUser(user, notification.id)
        .subscribe((user) => {});
      // if(user.notifications === null)
      //   user.notifications = [];
      // user.notifications.push(notification.id);
      // this.userService.updateUser(user).subscribe((user: User)=>{

      // });
    });
  }

  /**
   * Send a DELETE request to the api url
   * @param id of type number used apart of calling url
   *
   * @return Observable of the deleted Notification
   */
  deleteNotification(id: number): Observable<Notification> {
    let url = `${this.apiUrl}/${id}`;
    return this.http.delete<Notification>(url, this.httpOptions).pipe(
      tap((item) => {}),
      catchError(this.handleError<Notification>(`deleteNotification`))
    );
  }

  /**
   * Get all notifications from the api
   *
   * @returns an Observable of notification object array
   */
  getAllNotifications(): Observable<Notification[]> {
    if (this.userService.isSignedIn()) {
    } else {
    }
    return this.http.get<Notification[]>(this.apiUrl).pipe(
      tap((array) => {}),
      catchError(this.handleError<Notification[]>(`getAllNotifications`, []))
    );
  }

  /**
   * GET a single notification from the server
   *
   * @param id of id of the notification to get
   *
   * @return Observable of the requested Notification
   *  */
  getNotification(id: number): Observable<Notification> {
    const url = `${this.apiUrl}/${id}`;
    return this.http
      .get<Notification>(url)
      .pipe(
        catchError(this.handleError<Notification>(`getNotification id=${id}`))
      );
  }

  /** POST: add a new notification to the server
   * @pre Check to make sure that the id is not yet taken
   * @param notification - The new notification created by the notification based on custom data
   * @return an observable allowing the caller to wait on receiving the newly created notification until the notification creation has completed
   */
  addNotification(notification: Notification): Observable<Notification> {
    return this.http
      .post<Notification>(this.apiUrl, notification, this.httpOptions)
      .pipe(
        // tap((newNotification: Notification) =>

        // ),
        catchError(this.handleError<Notification>('addNotification'))
      );
  }

  /**
   * Simple Error handler will log it to browser console
   * returns an observable of specified type
   *
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      return of(result as T);
    };
  }
}
