import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrnithologyDashboardComponent } from './ornithology-dashboard.component';

describe('OrnithologyDashboardComponent', () => {
  let component: OrnithologyDashboardComponent;
  let fixture: ComponentFixture<OrnithologyDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrnithologyDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrnithologyDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
