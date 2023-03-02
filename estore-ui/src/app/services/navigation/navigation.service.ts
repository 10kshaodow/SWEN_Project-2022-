import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { NavigationEnd, Router } from '@angular/router';
import { ScrollService } from '../scroll/scroll.service';

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  private history: string[] = [];

  constructor(
    private router: Router,
    private location: Location,
    private scrollService: ScrollService
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.history.push(event.urlAfterRedirects);
        this.scrollService.setScroll(true);
      }
    });
  }

  navigate(url: string) {
    this.router.navigateByUrl(url);
  }

  getUrl() {
    return this.router.url;
  }

  onChange() {
    return this.router.events;
  }

  back() {
    this.history.pop();
    if (this.history.length > 0) {
      this.location.back();
    } else {
      this.router.navigateByUrl('/');
    }
  }
}
