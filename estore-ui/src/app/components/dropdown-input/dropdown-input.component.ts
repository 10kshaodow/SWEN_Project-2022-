import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-dropdown-input',
  templateUrl: './dropdown-input.component.html',
  styleUrls: ['./dropdown-input.component.css'],
})
export class DropdownInputComponent implements OnInit {
  @Input() title!: string;
  @Input() value?: string;
  @Output() valueChange: EventEmitter<string> = new EventEmitter();
  @Input() options: string[] = [];
  open: Boolean = false;

  constructor() {}

  ngOnInit(): void {}

  toggleOpen() {
    this.open = !this.open;
  }

  emitValue(newValue?: string) {
    this.valueChange.emit(newValue);
    this.toggleOpen();
  }
}
