import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Order } from 'src/app/types/Order';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrls: ['./account-info.component.css'],
})
export class AccountInfoComponent implements OnInit {
  @Input() user!: User;
  @Input() orders!: Order[];

  @Output() userChange: EventEmitter<User> = new EventEmitter<User>();
  @Output() onLogout: EventEmitter<void> = new EventEmitter<void>();
  @Output() onSave: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {
    console.log(JSON.stringify(this.user, null, 2));
  }

  setUser(changed: User) {
    this.user = changed;
    this.userChange.emit(this.user);
  }

  logout() {
    this.onLogout.emit();
  }

  save() {
    this.onSave.emit();
  }
}
