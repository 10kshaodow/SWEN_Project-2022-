import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from 'src/app/types/Product';
import { ProductService } from 'src/app/services/product/product.service';
import { OrderItem } from 'src/app/types/OrderItem';
import { UserService } from 'src/app/services/user/user.service';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';
import { ThisReceiver } from '@angular/compiler';
import { HealthReportService } from 'src/app/services/health-report/health-report.service';
import { HealthReport } from 'src/app/types/HealthReport';
import { OrderService } from 'src/app/services/order/order.service';

@Component({
  selector: 'app-my-falcon-view-page',
  templateUrl: './my-falcon-view.component.html',
  styleUrls: ['./my-falcon-view.component.css'],
})
export class MyFalconViewComponent implements OnInit {
  product!: Product;
  user!: User;
  healthReports?: HealthReport[];
  sponsors?: User[];
  activeReport?: HealthReport;
  sponsorshipDate!: Date;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
    private healthService: HealthReportService,
    private userService: UserService,
    private orderService: OrderService
  ) {}

  /**
   *
   */
  ngOnInit(): void {
    this.getProduct();
    this.userService.userCurrentlySignedIn.subscribe((signedIn) => {
      if (!signedIn) return;
      this.getCurrentUser();
    });

    this.getCurrentUser();
  }

  /**
   * Ask the server for the specified
   * product from the specified route id
   */
  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id).subscribe((product) => {
      if (!product) {
        this.router.navigate(['/home']);
        return;
      }
      this.product = product;
      this.getHealthReports();
      this.getSponsorshipDate();
    });
  }

  /**
   * Ask the server to search for the
   * health reports of the specified falcon
   * and only if the product is a falocon with healthReports
   */
  getHealthReports() {
    if (this.product.productType != 1) return;
    this.healthService
      .searchHealthReports(this.product.id)
      .subscribe((reports) => {
        this.healthReports = reports;
        if (reports && reports.length > 0) {
          this.activeReport = reports[0];
        }
      });
  }

  /**
   * Checks to see if the current user is signed in
   * @returns true if signed in, false otherwise
   */
  isUserSignedIn(): boolean {
    return this.userService.isSignedIn();
  }

  getCurrentUser(): void {
    if (this.userService.isSignedIn()) {
      this.user = this.userService.getCurrentUser();
    }
  }

  doesUserSponsorFalcon(): boolean {
    if (this.product && this.product.productType == 1) {
      if (this.user) {
        for (let i = 0; i < this.product.sponsors.length; i++) {
          if (this.product.sponsors[i] === this.user.username) {
            return true;
          }
        }
      }
    }

    return false;
  }

  getSponsorshipDate() {
    if (this.user && this.product) {
      for (let i = 0; i < this.user.orderHistory.length; i++) {
        let orderNumber = Number(this.user.orderHistory[i]);
        this.orderService.getOrder(orderNumber).subscribe((_order) => {
          console.log('Order id:' + _order.id);
          for (let j = 0; j < _order.orderItemList.length; j++) {
            if (_order.orderItemList[j].productID == this.product.id) {
              this.sponsorshipDate = _order.orderDate;
            }
          }
        });
      }
    }
  }

  /**
   * Set the active health report to a the selected report
   */
  setActiveReport(active: HealthReport) {
    if (active.reportID == this.activeReport?.reportID) {
      this.activeReport = undefined;
      return;
    }
    this.activeReport = active;
  }

  /**
   * Exactly what it says
   */
  dateTimeToString(dateTime: Date) {
    let date = new Date(dateTime);
    return date.toLocaleDateString();
  }

  // navigation function
  toHome() {
    this.router.navigate(['/']);
  }

  // navigation function
  toLogin() {
    this.router.navigate(['sign_in']);
  }

  toHealthReport(id: number) {
    this.router.navigate([`/health-report/${id}`]);
  }
}
