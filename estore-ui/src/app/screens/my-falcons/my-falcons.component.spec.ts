import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFalconsComponent } from './my-falcons.component';

describe('MyFalconsComponent', () => {
  let component: MyFalconsComponent;
  let fixture: ComponentFixture<MyFalconsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyFalconsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyFalconsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
