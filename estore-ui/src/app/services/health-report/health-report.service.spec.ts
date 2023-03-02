import { TestBed } from '@angular/core/testing';

import { HealthReportService } from './health-report.service';

describe('HealthReportService', () => {
  let service: HealthReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HealthReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
