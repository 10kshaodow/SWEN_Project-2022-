import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Notification } from 'src/app/types/Notification';

@Component({
  selector: 'app-notifi-card',
  templateUrl: './notifi-card.component.html',
  styleUrls: ['./notifi-card.component.css'],
})
export class NotifiCardComponent implements OnInit {
  @Input() large: boolean = false;
  @Input() notifi!: Notification;
  @Input() cancelable: boolean = true;
  @Input() clickable: boolean = false;

  @Output() onCancel: EventEmitter<void> = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  cancel(event: any) {
    this.onCancel.emit();
    event.stopPropagation(); 
  }
}
