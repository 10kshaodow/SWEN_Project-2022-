import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit {
  constructor(private userService: UserService) {}
  @Input() user!: User;
  showImmutableUsernameMessage: boolean = false;

  ngOnInit(): void {}

  /**
   * attempts to save user info to backend
   */
  saveUser(): void {
    if (this.userService.isSignedIn()) {
      this.userService.updateUser(this.user).subscribe((_user) => {
        console.log(
          'Updated user in user component' + JSON.stringify(_user, null, 2)
        );
        this.userService.setCurrentUser(this.user);
      });
    }
  }

  /* Setters For Inputs*/
  setUsersName(value: string) {
    this.user.name = value;
  }

  setUserCardNumber(value: string) {
    this.user.creditCard.number = value;
  }

  setUserCardMonth(value: string) {
    this.user.creditCard.expirationMonth = value;
  }

  setUserCardYear(value: string) {
    this.user.creditCard.expirationYear = value;
  }

  setUserCardCvv(value: string) {
    this.user.creditCard.cvv = value;
  }

  setUserStreet(value: string) {
    this.user.address.street = value;
  }

  setUserCity(value: string) {
    this.user.address.city = value;
  }

  setUserState(value: string) {
    this.user.address.state = value;
  }

  setUserCountry(value: string) {
    this.user.address.country = value;
  }

  setUserZip(value: string) {
    this.user.address.zipcode = value;
  }
}
