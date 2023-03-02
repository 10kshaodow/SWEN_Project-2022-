import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KeyNoteComponent } from './key-note.component';

describe('KeyNoteComponent', () => {
  let component: KeyNoteComponent;
  let fixture: ComponentFixture<KeyNoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ KeyNoteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KeyNoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
