import { Component, OnInit } from '@angular/core';

import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  admin!: User;
  visibleComponent!: 1 | 2 | 3 | 4;

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.visibleComponent = 3;

    // Guard against non admin user from accessing page
    if (!this.userService.isSignedIn()) {
      this.router.navigate(['/home']);
    }

    if (this.userService.getCurrentUser()?.type == UserType.Admin) {
      this.admin = this.userService.getCurrentUser();
    }

    // this is how we should be navigating not using the location way
    if (!(this.admin?.type == UserType.Admin)) {
      this.router.navigate(['/home']);
    }
  }

  // set the visible component based on selected tab
  setVisibleComponent(num: 1 | 2 | 3 | 4) {
    this.visibleComponent = num;
  }
}
