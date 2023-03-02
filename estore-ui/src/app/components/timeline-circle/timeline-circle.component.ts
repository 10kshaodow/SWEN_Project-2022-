import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-timeline-circle',
  templateUrl: './timeline-circle.component.html',
  styleUrls: ['./timeline-circle.component.css'],
})
export class TimelineCircleComponent implements OnInit {
  @Input() displaySource: string | null = null;
  @Input() active: Boolean = false;

  @Output() circleClick: EventEmitter<void> = new EventEmitter<void>();
  @Output() imageClick: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}
}
