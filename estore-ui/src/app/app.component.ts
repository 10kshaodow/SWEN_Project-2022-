import { Component, OnInit } from '@angular/core';
import { ScrollService } from './services/scroll/scroll.service';
import { UserService } from './services/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'estore-ui';

  scrollable!: boolean;

  constructor(
    private userService: UserService,
    private scrollService: ScrollService
  ) {}

  ngOnInit(): void {
    console.log('App Started');
    this.userService.restoreUser();
    this.listenToScroll();
  }

  listenToScroll() {
    this.scrollable = this.scrollService.getScroll();
    this.scrollService.listenToScroll().subscribe((value) => {
      this.scrollable = this.scrollService.getScroll();
    });
  }
}
