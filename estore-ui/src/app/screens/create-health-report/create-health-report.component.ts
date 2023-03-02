import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HealthReportService } from 'src/app/services/health-report/health-report.service';
import { UserService } from 'src/app/services/user/user.service';
import {
  DietStatus,
  FitnessStatus,
  HealthReport,
  SocialStatus,
} from 'src/app/types/HealthReport';
import { HealthStatus } from 'src/app/types/HealthStatus';
import { User } from 'src/app/types/User';
import { UserType } from 'src/app/types/UserType';
import { ImageSource } from 'src/app/types/ImageSource';
import { ProductService } from 'src/app/services/product/product.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { Product } from 'src/app/types/Product';
import { Notification } from 'src/app/types/Notification';
import { ThisReceiver } from '@angular/compiler';
import { NavigationService } from 'src/app/services/navigation/navigation.service';

@Component({
  selector: 'app-create-health-report',
  templateUrl: './create-health-report.component.html',
  styleUrls: ['./create-health-report.component.css'],
})
export class CreateHealthReportComponent implements OnInit {
  ornithologist!: User;
  falconID!: number;
  product!: Product;
  healthReport: HealthReport = { keyNotes: ['', '', ''] } as HealthReport;

  healthStatuses = Object.keys(HealthStatus);
  dietStatuses = Object.keys(DietStatus);
  fitnessStatuses = Object.keys(FitnessStatus);
  socialStatuses = Object.keys(SocialStatus);

  errorMessage: string | null = null;
  displaySource: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private navigation: NavigationService,
    private productService: ProductService,
    private healthReportService: HealthReportService,
    private userService: UserService,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.getFalconFromParams();
    // Guard against non ornithologist user from accessing page
    // if (!this.userService.isSignedIn()) {
    //   this.navigateBack();
    // }

    if (this.userService.getCurrentUser()?.type == UserType.Ornithologist) {
      this.ornithologist = this.userService.getCurrentUser();
    }

    // this is how we should be navigating not using the location way
    // if (!(this.ornithologist?.type == UserType.Ornithologist)) {
    //   this.navigateBack();
    // }
  }

  /**
   * Ask the server for the specified healthReport details
   *
   * healthReport is specified from the route parameters
   */
  private getHealthReportFromParams() {
    let paramId = this.route.snapshot.paramMap.get(`reportID`);
    if (paramId) {
      let id = Number(paramId);
      this.healthReportService
        .getHealthReport(id)
        .subscribe((_healthReport) => {
          this.healthReport = _healthReport;
          if (this.healthReport.fileName.length > 0) {
            this.displaySource = this.healthReport.fileName[0];
          }
        });
    } else {
      this.healthReport = this.defaultHealthReport(this.falconID);
    }
  }

  private getFalconFromParams() {
    let paramId = this.route.snapshot.paramMap.get(`falconID`);
    if (paramId) {
      let id = Number(paramId);
      this.productService.getProduct(id).subscribe((_falcon) => {
        if (_falcon) {
          this.falconID = id;
          this.product = _falcon;
          // console.log('Falcon ID: ' + this.falconID);
          this.getHealthReportFromParams();
        }
      });
    }
  }

  /**
   * If the healthReport is valid tell
   * the server to update the existing healthReport
   */
  private updateExistingHealthReport() {
    if (!this.validateState() || !this.healthReport.reportID) return;

    // console.log(`Updating`);
    // console.log(this.healthReport);
    // console.log();

    this.healthReportService
      .updateHealthReport(this.healthReport)
      .subscribe((onServer) => {
        // console.log(`API Response ${JSON.stringify(onServer, null, 2)}`);
        if (onServer) {
          let newHealthReport: boolean = false;
          this.addSponserNotifications(newHealthReport);
          this.navigateBack();
        } else {
          this.errorMessage =
            'Internal Error Updating Health Report Please Try again Later';
        }
      });
  }

  submit() {
    console.log('healthReport: ' + JSON.stringify(this.healthReport, null, 2));
    this.convertImageSource();
    if (!this.healthReport.reportID) {
      this.createNewHealthReport();
    } else {
      this.updateExistingHealthReport();
    }
  }

  /* Event Handlers for the input component to set the fields of the healthReport */

  setHealthDescription(message: string) {
    this.healthReport.healthDescription = message;
  }

  setHealthStatus(status: string): void {
    // console.log('Selected: ' + status);
    if (this.healthStatuses.includes(status)) {
      let selectedStatus = HealthStatus[status as keyof typeof HealthStatus];
      this.healthReport!.healthStatus = selectedStatus;
      // console.log('After select: ' + this.healthReport.healthStatus);
    }
  }

  setDiet(status: string): void {
    // console.log('Selected: ' + status);
    if (this.dietStatuses.includes(status)) {
      let selectedStatus = DietStatus[status as keyof typeof DietStatus];
      this.healthReport!.diet = selectedStatus;
      // console.log('After select: ' + this.healthReport.healthStatus);
    }
  }

  setFitness(status: string): void {
    // console.log('Selected: ' + status);
    if (this.fitnessStatuses.includes(status)) {
      let selectedStatus = FitnessStatus[status as keyof typeof FitnessStatus];
      this.healthReport!.fitness = selectedStatus;
      // console.log('After select: ' + this.healthReport.fitness);
    }
  }

  setSocial(status: string): void {
    // console.log('Selected: ' + status);
    if (this.socialStatuses.includes(status)) {
      let selectedStatus = SocialStatus[status as keyof typeof SocialStatus];
      this.healthReport!.social = selectedStatus;
      // console.log('After select: ' + this.healthReport.social);
    }
  }

  setReproductiveStatus(status: boolean): void {
    this.healthReport.reproductiveStatus = status;
  }

  setDisplaySource(base64: string | null) {
    this.displaySource = base64;
    console.log('base64: ' + base64);
  }

  setKeyNoteOne(note: string): void {
    this.healthReport.keyNotes[0] = note;
  }

  setKeyNoteTwo(note: string): void {
    this.healthReport.keyNotes[1] = note;
  }

  setKeyNoteThree(note: string): void {
    this.healthReport.keyNotes[2] = note;
  }

  /**
   * If the healthReport is valid tell
   * the server to create the new healthReport
   */
  private createNewHealthReport() {
    if (!this.validateState()) return;

    this.healthReportService
      .addHealthReport(this.healthReport as HealthReport)
      .subscribe((onServer) => {
        if (onServer) {
          let newHealthReport: boolean = true;
          this.addSponserNotifications(newHealthReport);
          this.navigateBack();
        } else {
          this.errorMessage =
            'Internal Error Creating Health Report Please Try again Later';
        }
      });
  }

  /**
   * adds notifications to the list that has sponsered this bird
   */
  private addSponserNotifications(newReport: boolean): void {
    this.productService
      .getProduct(this.falconID)
      .subscribe((falcon: Product) => {
        let notification: Notification = {
          id: 0,
          title: newReport ? 'New Health Report!' : 'Updated Health Report!',
          message: 'Your falcon ' + falcon.name + ' has a new health report',
        } as Notification;
        this.notificationService
          .addNotification(notification)
          .subscribe((retNotif) => {
            falcon.sponsors.forEach((userId) => {
              this.userService.getUser(userId).subscribe((user) => {
                this.notificationService.addNotificationToUsers(retNotif, [
                  user,
                ]);
              });
            });
          });
      });
  }

  /**
   * Validate that all the necassary inputs are filled in
   */
  private validateState() {
    this.errorMessage = null;
    if (this.healthReport.healthDescription.length <= 0) {
      this.errorMessage = `Missing Health Description`;
      return false;
    }
    if (this.healthReport.healthStatus == null) {
      this.errorMessage = `Select a Health Status`;
      return false;
    }
    if (this.healthReport.diet == null) {
      this.errorMessage = `Select a Diet Status`;
      return false;
    }
    if (this.healthReport.fitness == null) {
      this.errorMessage = `Select a fitness Status`;
      return false;
    }
    if (this.healthReport.social == null) {
      this.errorMessage = `Select a social Status`;
      return false;
    }
    return true;
  }

  /**
   * When the file input changes get
   * the file and ensure that a file was selected
   */
  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    console.log(JSON.stringify(input, null, 2));
    console.log();
    let fileList: FileList | null = input.files;
    if (!fileList) return;
    console.log(JSON.stringify(fileList, null, 2));
    this.saveFileProperties(fileList);
  }

  /**
   * manipulate the file object to save the
   * base64 format and display the base64 format of the file
   *
   * @param file file object to be manipulated for display and saving
   */
  private saveFileProperties(fileList: FileList) {
    for (let i: number = 0; i < fileList.length; i++) {
      const reader = new FileReader();
      let file = fileList.item(i);
      if (!file) continue;
      const fileName = file.name;
      const fileType = file.type;
      let replacestring = 'data:' + fileType.trim() + ';base64,';

      reader.readAsDataURL(file);
      reader.onload = (_event) => {
        console.log('On Load: ' + fileName);
        if (!reader.result) return;
        if (i == 0) {
          this.setDisplaySource(reader.result as string);
        }

        this.healthReport.fileSource.push({
          base64: reader.result.toString(), //.replace(replacestring, ' '),
          orginalName: fileName,
        });
      };
    }
  }

  private convertImageSource(): void {
    let fileIndicator = ';base64,';
    let typeIndicator = '.';

    for (let i: number = 0; i < this.healthReport.fileSource.length; i++) {
      let source = this.healthReport.fileSource[i];
      let fileTypeIndex = source.base64.indexOf(fileIndicator);
      let base64 = source.base64.substring(
        fileTypeIndex + fileIndicator.length
      );
      this.healthReport.fileSource[i].base64 = base64;
    }
  }

  private findFileSourceIndex(name: string) {
    for (let i: number = 0; i < this.healthReport.fileSource.length; i++) {
      let filename = this.healthReport.fileSource[i].orginalName;
      if (filename === name) {
        return i;
      }
    }

    return -1;
  }

  private findFileNameIndex(name: string) {
    for (let i: number = 0; i < this.healthReport.fileName.length; i++) {
      let filename = this.healthReport.fileName[i];
      if (filename === name) {
        return i;
      }
    }

    return -1;
  }

  getFileName(fileName: string): String {
    let urlString = 'http://localhost:8080/static/';
    if (fileName) {
      if (fileName.indexOf(urlString) > -1) {
        return fileName.substring(urlString.length);
      } else {
        return fileName;
      }
    }

    return 'file.jpg';
  }

  public removeFileSource(name: string) {
    let index = this.findFileSourceIndex(name);
    if (index != -1) {
      this.healthReport.fileSource.splice(index, 1);
      if (this.healthReport.fileSource.length > 0) {
        this.setDisplaySource(this.healthReport.fileSource[0].base64);
      } else {
        this.setDisplaySource(null);
      }
    }
  }

  public removeFileName(name: string) {
    let index = this.findFileNameIndex(name);
    if (index != -1) {
      this.healthReport.fileName.splice(index, 1);
      if (this.healthReport.fileName.length > 0) {
        this.setDisplaySource(this.healthReport.fileName[0]);
      } else {
        this.setDisplaySource(null);
      }
    }
  }

  private defaultHealthReport(falconID: number): HealthReport {
    let healthStatus: HealthStatus = HealthStatus.Superb;
    let healthDescription: string = '';
    let reportDate: Date = new Date();
    let keyNotes: String[] = ['', '', ''];
    let diet: DietStatus = DietStatus.Nutritional;
    let fitness: FitnessStatus = FitnessStatus.Active;
    let social: SocialStatus = SocialStatus.Lively;
    let reproductiveStatus: boolean = false;
    let fileName: string[] = [];
    let fileSource: ImageSource[] = [];

    let healthReport: HealthReport = {
      falconID,
      reportDate,
      healthStatus,
      healthDescription,
      keyNotes,
      diet,
      fitness,
      social,
      reproductiveStatus,
      fileName,
      fileSource,
    } as HealthReport;

    return healthReport;
  }

  /**
   * Navigate to the previous page
   */
  navigateBack() {
    this.navigation.back();
  }
}
