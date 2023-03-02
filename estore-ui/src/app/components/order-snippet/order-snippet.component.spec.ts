import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderSnippetComponent } from './order-snippet.component';

describe('OrderSnippetComponent', () => {
  let component: OrderSnippetComponent;
  let fixture: ComponentFixture<OrderSnippetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderSnippetComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderSnippetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
