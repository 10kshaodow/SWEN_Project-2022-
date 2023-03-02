import {
  Component,
  DoCheck,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-text-input',
  templateUrl: './text-input.component.html',
  styleUrls: ['./text-input.component.css'],
})
export class TextInputComponent implements OnInit, DoCheck {
  @Input() value?: string;
  @Input() numberValue?: number;
  @Output() numberValueChange: EventEmitter<number> =
    new EventEmitter<number>();
  @Output() valueChange: EventEmitter<string> = new EventEmitter<string>();
  @Input() title!: string;
  @Input() disabled: boolean = false;
  @Input() inputType: 'large' | 'expanded' | 'number' | 'email' = 'large';

  constructor() {}

  // immediatly make sure parent component
  // and this one are in sync with their values
  ngDoCheck(): void {
    if (this.value) {
      this.valueChange.next(this.value);
    }
    if (this.numberValue) {
      this.numberValueChange.next(this.numberValue);
    }
  }

  ngOnInit(): void {}

  // send the event to the appropriate handler that being a number
  // handler or a string handler in provided in the parent component
  onInput(event: Event) {
    event.preventDefault();
    const input = event.target as HTMLInputElement;
    const value = input.value;

    if (this.inputType === 'number') {
      this.numberValue = parseFloat(value);
      this.numberValueChange.emit(this.numberValue);
    } else {
      this.value = value;
      this.valueChange.emit(this.value);
    }
  }
}
