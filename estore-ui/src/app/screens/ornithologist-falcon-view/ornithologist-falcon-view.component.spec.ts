import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrnithologistFalconViewComponent } from './ornithologist-falcon-view.component';

describe('OrnithologistFalconViewComponent', () => {
  let component: OrnithologistFalconViewComponent;
  let fixture: ComponentFixture<OrnithologistFalconViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrnithologistFalconViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrnithologistFalconViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
