import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  username: string = '';

  @Output() signInEvent: EventEmitter<string> = new EventEmitter<string>();
  @Output() createUserEvent: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  setUsername(name: string) {
    this.username = name;
  }

  signIn() {
    this.signInEvent.emit(this.username);
  }

  createUser() {
    this.createUserEvent.emit();
  }
}
