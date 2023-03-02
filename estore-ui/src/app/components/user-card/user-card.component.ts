import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'src/app/types/User';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css'],
})
export class UserCardComponent implements OnInit {
  @Input() user!: User;
  @Input() clickable: boolean = false;
  @Input() selected: boolean = false;

  @Output() onClick: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  click() {
    this.onClick.emit();
  }
}
