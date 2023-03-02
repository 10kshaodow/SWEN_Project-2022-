import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateHealthReportComponent } from './create-health-report.component';

describe('CreateHealthReportComponent', () => {
  let component: CreateHealthReportComponent;
  let fixture: ComponentFixture<CreateHealthReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateHealthReportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateHealthReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
