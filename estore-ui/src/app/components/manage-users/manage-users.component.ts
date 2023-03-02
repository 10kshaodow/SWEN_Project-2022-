import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],
})
export class ManageUsersComponent implements OnInit {
  allUsers: User[] = [];
  selectedUser?: User = undefined;
  userIsSelected: boolean = false;
  showImmutableUsernameMessage: boolean = false;
  showCantDeleteAdminMessage: boolean = false;
  userTypes = Object.keys(UserType);
  currentUserType: UserType = UserType.Customer;

  constructor(private userService: UserService) {
    console.log(JSON.stringify(this.userTypes));
  }

  ngOnInit(): void {
    this.reloadUsers();
  }

  userSelected(user: User) {
    console.log(`User Updated`);
    this.selectedUser = user;
    this.currentUserType = this.selectedUser.type;
    this.showCantDeleteAdminMessage = false;
    this.userIsSelected = true;
  }

  saveUser() {
    console.log('updated user: ' + JSON.stringify(this.selectedUser, null, 2));
    this.userService.updateUser(this.selectedUser!).subscribe((user) => {
      // display a message
      this.reloadUsers();
    });
    this.userUnselected();
  }

  cancelUser() {
    this.reloadUsers();
    this.userUnselected();
  }

  userUnselected() {
    this.userIsSelected = false;
    this.showCantDeleteAdminMessage = false;
    this.selectedUser = undefined;
  }

  setUserType(type: string): void {
    console.log('Selected: ' + type);
    if (this.userTypes.includes(type)) {
      let selectedType = UserType[type as keyof typeof UserType];
      this.selectedUser!.type = selectedType;
      console.log('After select: ' + this.selectedUser?.type);
    }
  }

  deleteUser() {
    if (this.selectedUser!.username === 'admin') {
      this.showCantDeleteAdminMessage = true;
      return;
    }
    this.userService
      .deleteUser(this.selectedUser!.username)
      .subscribe((success) => {
        if (success) {
          // need a success message
        } else {
          // need a failure message
        }
        this.reloadUsers();
      });
    this.userUnselected();
  }

  reloadUsers() {
    this.userService.getAllUsers().subscribe((users) => {
      this.allUsers = users;
      console.log('got ' + users.length + ' users');
    });
  }

  /* Setters for selected user*/
  setUsersName(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.name = value;
  }

  setUserCardNumber(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.creditCard.number = value;
  }

  setUserCardMonth(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.creditCard.expirationMonth = value;
  }

  setUserCardYear(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.creditCard.expirationYear = value;
  }

  setUserCardCvv(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.creditCard.cvv = value;
  }

  setUserStreet(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.address.street = value;
  }

  setUserCity(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.address.city = value;
  }

  setUserState(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.address.state = value;
  }

  setUserCountry(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.address.country = value;
  }

  setUserZip(value: string) {
    if (!this.selectedUser) return;
    this.selectedUser.address.zipcode = value;
  }
}
