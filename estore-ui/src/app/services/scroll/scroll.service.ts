import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ScrollService {
  private scrollable: boolean = true;
  private scrollSubject: Subject<boolean> = new Subject();

  constructor() {
    this.scrollSubject.subscribe((value) => {
      console.log(`Setting Scroll to ${value}`);
      this.scrollable = value;
    });
  }

  listenToScroll() {
    return this.scrollSubject;
  }

  getScroll() {
    return this.scrollable;
  }

  setScroll(value: boolean) {
    this.scrollSubject.next(value);
  }
}
