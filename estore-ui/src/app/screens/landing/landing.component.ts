import { Component, OnInit } from '@angular/core';
import { NavigationService } from 'src/app/services/navigation/navigation.service';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css'],
})
export class LandingComponent implements OnInit {
  constructor(private navigation: NavigationService) {}

  ngOnInit(): void {}

  toHomePage() {
    this.navigation.navigate('/home');
  }
}
