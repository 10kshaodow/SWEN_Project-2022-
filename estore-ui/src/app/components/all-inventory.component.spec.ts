import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllInventoryComponent } from './all-inventory.component';

describe('AllInventoryComponent', () => {
  let component: AllInventoryComponent;
  let fixture: ComponentFixture<AllInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllInventoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
