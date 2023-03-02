import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewHealthReportComponent } from './view-health-report.component';

describe('ViewHealthReportComponent', () => {
  let component: ViewHealthReportComponent;
  let fixture: ComponentFixture<ViewHealthReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewHealthReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewHealthReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
