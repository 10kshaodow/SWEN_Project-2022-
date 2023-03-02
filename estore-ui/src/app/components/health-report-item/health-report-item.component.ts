import { Component, Input, OnInit } from '@angular/core';
import { HealthReport } from 'src/app/types/HealthReport';

@Component({
  selector: 'app-health-report-item',
  templateUrl: './health-report-item.component.html',
  styleUrls: ['./health-report-item.component.css'],
})
export class HealthReportItemComponent implements OnInit {
  @Input() healthReport!: HealthReport;
  @Input() backupSource?: string;

  constructor() {}

  ngOnInit(): void {}

  /**
   * Exactly what it says
   */
  dateTimeToString(dateTime: Date) {
    let date = new Date(dateTime);
    return date.toLocaleDateString();
  }
}
