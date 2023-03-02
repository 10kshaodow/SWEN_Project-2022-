import { Injectable } from '@angular/core';
import { StorageMap } from '@ngx-pwa/local-storage';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap, Observable, of, observable } from 'rxjs';

import { User } from 'src/app/types/User';
import { Address } from 'src/app/types/Address';
import { UsernameExistsError } from 'src/app/errors/UsernameExistsError';
import { UsernameNotFoundError } from 'src/app/errors/UsernameNotFoundError';
import { NotSignedInError } from 'src/app/errors/NotSignedInError';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl = 'http://localhost:8080/users';

  // used to check when a user signs in and out
  userCurrentlySignedIn: Subject<boolean> = new Subject<boolean>();
  // currentSignedInUser: Subject<User> = new Subject<User>();

  private signedIn: boolean = false;
  private currentUser?: User = undefined;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  constructor(private http: HttpClient, private session: StorageMap) {}

  /* HOW TO USE: 
  
  1) check if someone is signed in: userService.isSignedIn()
  2) get the signed in user by userService.getCurrentUser()
  calling getCurrentUser() with no one signed in will throw a NotSignedInError, so make 
  sure you check if the user is signed in first
  3) subscribe to userCurrentlySignedIn to get updates when the current user sign in 
  status changes

  use <app-user-sign-in /> component to show a sign in screen

  */

  /**
   * signs out a user if one is signed in
   */
  signOut() {
    this.currentUser = undefined;
    this.signedIn = false;
    this.userCurrentlySignedIn.next(false);
    this.session.clear().subscribe(() => {});
  }

  /**
   * gets the current sign in state
   *
   * @returns true if a user is signed in, false if not
   */
  isSignedIn(): boolean {
    return this.signedIn;
  }

  /**
   * gets the current signed in user
   *
   * @returns the current signed in user
   *
   * @throws NotSignedInError when a user is not signed in
   */
  getCurrentUser(): User {
    if (!this.signedIn) throw new NotSignedInError('User is not signed in');

    return this.currentUser!;
  }

  /**
   * sets the current signed in user
   *
   * @params the user to assign to the current user
   *
   * @throws NotSignedInError when a user is not signed in
   */
  setCurrentUser(user: User): void {
    this.signedIn = true;
    this.currentUser = user;
    this.userCurrentlySignedIn.next(true);
    this.session.set('userName', user.username).subscribe(() => {});
  }

  /**
   * Check if the user has previously signed in
   * if so get their details from the backend
   * to sign them in on the front end
   */
  restoreUser() {
    this.session.get('userName').subscribe((name) => {
      if (name) {
        this.getUser(name as string).subscribe((user) => {
          if (user) {
            this.setCurrentUser(user);
          }
        });
      }
    });
  }

  /**
   * Send a DELETE request to the api url
   * @param userID of type number used apart of calling url
   *
   * @return Observable of the deleted User
   */
  deleteUser(userID: string): Observable<User> {
    let url = `${this.apiUrl}/${userID}`;
    return this.http.delete<User>(url, this.httpOptions).pipe(
      tap((item) => {}),
      catchError(this.handleError<User>(`deleteUser`))
    );
  }

  /**
   * Get all users from the api
   *
   * @returns an Observable of user object array
   */
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      tap((array) => {}),
      catchError(this.handleError<User[]>(`getAllUsers`, []))
    );
  }

  /**
   * GET a single user from the server
   *
   * @param username of type string used in the calling url
   *
   * @return Observable of the requested User
   *  */
  getUser(username: string): Observable<User> {
    const url = `${this.apiUrl}/${username}`;
    return this.http
      .get<User>(url)
      .pipe(catchError(this.handleError<User>(`getUser username=${username}`)));
  }

  /**
   * checks if a username already exists
   *
   * @param username the username to check
   * @returns true if the username exists, false otherwise
   */
  checkUsernameExists(username: string): Observable<boolean> {
    const url = `${this.apiUrl}/exists/${username}`;
    return this.http.get<boolean>(url).pipe(
      // tap((response: boolean) =>

      // ),
      catchError(
        this.handleError<boolean>(`checkUsername username=${username}`)
      )
    );
  }

  /** PUT: update the user on the server
   * @param user
   * @return an observable allowing the caller to wait on receiving the updated user until the user update has completed
   */
  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.apiUrl, user, this.httpOptions).pipe(
      // tap((response: User) =>

      // ),
      catchError(this.handleError<User>('updateUser'))
    );
  }

  /** PUT: add a notification to a user
   * @param user the user to update
   * @param notificationId the notification number
   * @return an observable allowing the caller to wait on receiving the updated user until the user update has completed
   */
  addNotificationToUser(user: User, notificationId: number): Observable<User> {
    return this.http
      .put<User>(
        this.apiUrl + '/' + user.username + '/' + notificationId,
        this.httpOptions
      )
      .pipe(
        // tap((response: User) =>

        // ),
        catchError(this.handleError<User>('updateUser'))
      );
  }

  /** POST: add a new user to the server
   * @pre Check to make sure that the username is not yet taken
   * @param user - The new user created by the user based on custom data
   * @return an observable allowing the caller to wait on receiving the newly created user until the user creation has completed
   */
  addUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user, this.httpOptions).pipe(
      // tap((newUser: User) =>

      // ),
      catchError(this.handleError<User>('addUser'))
    );
  }

  /*
      Simple Error handler will log it to browser console
  
      returns an observable of specified type
    */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);

      return of(result as T);
    };
  }
}
