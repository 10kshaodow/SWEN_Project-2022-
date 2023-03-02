import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotifiCardComponent } from './notifi-card.component';

describe('NotifiCardComponent', () => {
  let component: NotifiCardComponent;
  let fixture: ComponentFixture<NotifiCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotifiCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotifiCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
