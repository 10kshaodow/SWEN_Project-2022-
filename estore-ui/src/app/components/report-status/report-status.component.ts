import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-report-status',
  templateUrl: './report-status.component.html',
  styleUrls: ['./report-status.component.css'],
})
export class ReportStatusComponent implements OnInit {
  @Input() title!: string;
  @Input() status!: string;
  @Input() color: 'green' | 'yellow' | 'orange' | 'red' = 'green';

  constructor() {}

  ngOnInit(): void {}
}
