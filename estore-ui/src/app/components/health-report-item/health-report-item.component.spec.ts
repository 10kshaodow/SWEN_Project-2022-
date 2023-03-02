import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthReportItemComponent } from './health-report-item.component';

describe('HealthReportItemComponent', () => {
  let component: HealthReportItemComponent;
  let fixture: ComponentFixture<HealthReportItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HealthReportItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HealthReportItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
