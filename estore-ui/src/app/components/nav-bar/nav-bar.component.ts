import { Component, OnInit } from '@angular/core';
import { NavigationEnd } from '@angular/router';
import { NavigationService } from 'src/app/services/navigation/navigation.service';
import { UserService } from 'src/app/services/user/user.service';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
})
export class NavBarComponent implements OnInit {
  isLandingPage = false;

  userSignedIn = false;
  home = false;
  admin = false;
  cart = false;
  orno = false;
  falco = false;

  constructor(
    private userService: UserService,
    private navigation: NavigationService
  ) {}

  showHome(): void {
    if (this.userService.isSignedIn()) {
      let user = this.userService.getCurrentUser();
      if (user.type.toString() === UserType.Ornithologist.toString()) {
        this.home = false;
        return;
      }
    }
    this.home = true;
  }

  showAdministration(): void {
    if (this.userService.isSignedIn()) {
      let user = this.userService.getCurrentUser();
      if (user.type.toString() === UserType.Admin.toString()) {
        this.admin = true;
        return;
      }
    }
    this.admin = false;
  }

  showCart(): void {
    if (this.userService.isSignedIn()) {
      let user = this.userService.getCurrentUser();
      if (user.type.toString() === UserType.Customer.toString()) {
        this.cart = true;
        return;
      }
    }
    this.cart = false;
  }

  showMyFalcons(): void {
    if (this.userService.isSignedIn()) {
      let user = this.userService.getCurrentUser();
      if (user.type.toString() === UserType.Customer.toString()) {
        this.falco = true;
        return;
      }
    }
    this.falco = false;
  }

  showOrnithologyDashboard(): void {
    if (this.userService.isSignedIn()) {
      let user = this.userService.getCurrentUser();
      if (user.type.toString() === UserType.Ornithologist.toString()) {
        this.orno = true;
        return;
      }
    }
    this.orno = false;
  }

  isOnLanding() {
    this.isLandingPage = this.navigation.getUrl() === '/';
  }

  checkState() {
    this.userSignedIn = this.userService.isSignedIn();
    this.showHome();
    this.showAdministration();
    this.showCart();
    this.showOrnithologyDashboard();
    this.showMyFalcons();
    this.isOnLanding();
  }

  ngOnInit(): void {
    this.userService.userCurrentlySignedIn.subscribe((signedIn: boolean) => {
      this.checkState();
    });

    this.navigation.onChange().subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.checkState();
      }
    });

    this.checkState();
  }
}
