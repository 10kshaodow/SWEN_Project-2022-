import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-key-note',
  templateUrl: './key-note.component.html',
  styleUrls: ['./key-note.component.css'],
})
export class KeyNoteComponent implements OnInit {
  @Input() note!: string;
  @Input() hairline: boolean = true;

  constructor() {}

  ngOnInit(): void {}
}
