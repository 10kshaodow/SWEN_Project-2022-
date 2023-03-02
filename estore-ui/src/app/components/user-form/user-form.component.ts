import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  userTypes = Object.keys(UserType);

  @Input() user!: User;
  @Input() typeChange: boolean = false;
  @Output() userChange: EventEmitter<User> = new EventEmitter<User>();

  constructor() {}

  ngOnInit(): void {}

  sendUser() {
    this.userChange.emit(this.user);
  }

  setUsersName(value: string) {
    this.user.name = value;
    this.sendUser();
  }

  setUserCardNumber(value: string) {
    this.user.creditCard.number = value;
    this.sendUser();
  }

  setUserCardMonth(value: string) {
    this.user.creditCard.expirationMonth = value;
    this.sendUser();
  }

  setUserCardYear(value: string) {
    this.user.creditCard.expirationYear = value;
    this.sendUser();
  }

  setUserCardCvv(value: string) {
    this.user.creditCard.cvv = value;
    this.sendUser();
  }

  setUserStreet(value: string) {
    this.user.address.street = value;
    this.sendUser();
  }

  setUserCity(value: string) {
    this.user.address.city = value;
    this.sendUser();
  }

  setUserState(value: string) {
    this.user.address.state = value;
    this.sendUser();
  }

  setUserCountry(value: string) {
    this.user.address.country = value;
    this.sendUser();
  }

  setUserZip(value: string) {
    this.user.address.zipcode = value;
    this.sendUser();
  }

  setUserType(value: string) {
    this.user.type = value as UserType;
    this.sendUser();
  }
}
