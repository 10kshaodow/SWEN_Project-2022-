import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimelineCircleComponent } from './timeline-circle.component';

describe('TimelineCircleComponent', () => {
  let component: TimelineCircleComponent;
  let fixture: ComponentFixture<TimelineCircleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimelineCircleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimelineCircleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
