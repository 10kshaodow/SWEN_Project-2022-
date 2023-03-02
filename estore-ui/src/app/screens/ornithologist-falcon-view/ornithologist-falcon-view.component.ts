import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from 'src/app/services/product/product.service';
import { Product } from 'src/app/types/Product';
import { HealthReportService } from 'src/app/services/health-report/health-report.service';
import { HealthReport } from 'src/app/types/HealthReport';
import { UserService } from 'src/app/services/user/user.service';
import { UserType } from 'src/app/types/UserType';

@Component({
  selector: 'app-ornithologist-falcon-view',
  templateUrl: './ornithologist-falcon-view.component.html',
  styleUrls: ['./ornithologist-falcon-view.component.css'],
})
export class OrnithologistFalconViewComponent implements OnInit {
  constructor(
    private router: Router,
    private productService: ProductService,
    private healthReportService: HealthReportService,
    private userService: UserService,
    private route: ActivatedRoute
  ) {}

  falcon: Product = {} as Product;
  falconFound: boolean = false;
  healthReports: HealthReport[] = [];
  activeReport?: HealthReport;
  showModal: boolean = false;

  ngOnInit(): void {
    this.getFalconFromParams();
  }

  /**
   * Ask the server for the specified order details
   *
   * order is specified from the route parameters
   */
  private getFalconFromParams() {
    let paramId = this.route.snapshot.paramMap.get(`id`);
    if (paramId) {
      let id = Number(paramId);
      this.productService.getProduct(id).subscribe((_falcon) => {
        this.falcon = _falcon;
        this.getHistoricHealthReports();
      });
    }
  }

  getHistoricHealthReports(): void {
    this.healthReportService
      .searchHealthReports(this.falcon.id)
      .subscribe((_healthReports) => {
        console.log('Historical Health reports: ' + _healthReports);
        this.healthReports = _healthReports;
        if (_healthReports && _healthReports.length > 0) {
          this.activeReport = _healthReports[0];
        }
      });
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
    console.log(JSON.stringify(active, null, 2));
    console.log();
  }

  toCreateHealthReport() {
    this.router.navigate([`/create-health-report/${this.falcon.id}`]);
  }

  toUpdateHealthReport() {
    if (this.activeReport) {
      this.router.navigate([
        `/create-health-report/${this.falcon.id}/${this.activeReport.reportID}`,
      ]);
    }
  }

  /**
   * Exactly what it says
   */
  dateTimeToString(dateTime: Date) {
    let date = new Date(dateTime);
    return (
      date.toLocaleDateString() +
      ' ' +
      date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    );
  }

  isUserSignedIn(): boolean {
    if (this.userService.isSignedIn()) {
      return true;
    }

    return false;
  }

  isUserOrnithologist(): boolean {
    if (this.userService.isSignedIn()) {
      if (this.userService.getCurrentUser().type == UserType.Ornithologist) {
        return true;
      }
    }

    return false;
  }

  // navigate to the selected health report page
  toHealthReport(id: number) {
    this.router.navigate([`/health-report/${id}`]);
  }
}
