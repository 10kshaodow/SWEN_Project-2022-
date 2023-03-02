import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
})
export class CreateAccountComponent implements OnInit {
  username: string = '';

  @Output() signInEvent: EventEmitter<void> = new EventEmitter<void>();
  @Output() createUserEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor() {}

  ngOnInit(): void {}

  setUsername(name: string) {
    this.username = name;
  }

  signIn() {
    this.signInEvent.emit();
  }

  createUser() {
    this.createUserEvent.emit(this.username);
  }
}
