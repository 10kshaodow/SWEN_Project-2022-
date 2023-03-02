import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyFalconViewComponent } from './my-falcon-view.component';

describe('MyFalconViewComponent', () => {
  let component: MyFalconViewComponent;
  let fixture: ComponentFixture<MyFalconViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyFalconViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyFalconViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
