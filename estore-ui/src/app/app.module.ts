import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRouting } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { LandingComponent } from './screens/landing/landing.component';

// import { UserService } from './services/user/user.service';

import { CreateProductComponent } from './screens/create-product/create-product.component';
import { HomeComponent } from './screens/home/home.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { ProductComponent } from './components/product/product.component';
import { UserSignInComponent } from './screens/user-sign-in/user-sign-in.component';
import { CartComponent } from './screens/cart/cart.component';
import { AdminComponent } from './screens/admin/admin.component';
import { ManageProductsComponent } from './components/manage-products/manage-products.component';
import { ManageOrdersComponent } from './components/manage-orders/manage-orders.component';
import { ManageUsersComponent } from './components/manage-users/manage-users.component';
import { TextInputComponent } from './components/text-input/text-input.component';
import { UserComponent } from './components/user/user.component';
import { OrderItemComponent } from './components/order-item/order-item.component';
import { CheckoutComponent } from './screens/checkout/checkout.component';
import { OrderComponent } from './screens/order/order.component';
import { ProductPageComponent } from './screens/product-page/product-page.component';
import { CheckboxComponent } from './components/checkbox/checkbox.component';
import { OrnithologyDashboardComponent } from './screens/ornithology-dashboard/ornithology-dashboard.component';
import { OrnithologistFalconViewComponent } from './screens/ornithologist-falcon-view/ornithologist-falcon-view.component';
import { ManageNotificationsComponent } from './components/manage-notifications/manage-notifications.component';
import { CreateHealthReportComponent } from './screens/create-health-report/create-health-report.component';
import { TimelineCircleComponent } from './components/timeline-circle/timeline-circle.component';
import { ButtonComponent } from './components/button/button.component';
import { DropdownInputComponent } from './components/dropdown-input/dropdown-input.component';
import { ViewNotificationComponent } from './components/view-notification/view-notification.component';
import { AllNotificationsComponent } from './screens/all-notifications/all-notifications.component';
import { MyFalconsComponent } from './screens/my-falcons/my-falcons.component';
import { MyFalconViewComponent } from './screens/my-falcon-view/my-falcon-view.component';
import { ViewHealthReportComponent } from './screens/view-health-report/view-health-report.component';
import { DetailHeaderComponent } from './components/detail-header/detail-header.component';
import { HealthReportItemComponent } from './components/health-report-item/health-report-item.component';
import { UserCardComponent } from './components/user-card/user-card.component';
import { LoginComponent } from './components/login/login.component';
import { ErrorMessageComponent } from './components/error-message/error-message.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';
import { AccountInfoComponent } from './components/account-info/account-info.component';
import { UserFormComponent } from './components/user-form/user-form.component';
import { OrderSnippetComponent } from './components/order-snippet/order-snippet.component';
import { NotifiCardComponent } from './components/notifi-card/notifi-card.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { ModalComponent } from './components/modal/modal.component';
import { KeyNoteComponent } from './components/key-note/key-note.component';
import { ReportStatusComponent } from './components/report-status/report-status.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingComponent,
    HomeComponent,
    NavBarComponent,
    ProductComponent,
    UserSignInComponent,
    CartComponent,
    AdminComponent,
    ManageProductsComponent,
    ManageOrdersComponent,
    ManageUsersComponent,
    TextInputComponent,
    ProductPageComponent,
    ManageNotificationsComponent,
    ButtonComponent,
    DropdownInputComponent,
    ViewNotificationComponent,
    AllNotificationsComponent,
    DetailHeaderComponent,
    HealthReportItemComponent,
    UserCardComponent,
    LoginComponent,
    ErrorMessageComponent,
    CreateAccountComponent,
    AccountInfoComponent,
    UserFormComponent,
    OrderSnippetComponent,
    ProductFormComponent,
    ModalComponent,
    NotifiCardComponent,

    CreateProductComponent,
    UserComponent,
    OrderItemComponent,
    CheckoutComponent,
    OrderComponent,
    CheckboxComponent,
    OrnithologyDashboardComponent,
    OrnithologistFalconViewComponent,
    CreateHealthReportComponent,
    TimelineCircleComponent,
    MyFalconsComponent,
    MyFalconViewComponent,
    ViewHealthReportComponent,
    KeyNoteComponent,
    ReportStatusComponent,
  ],
  imports: [BrowserModule, AppRouting, HttpClientModule, FormsModule],
  providers: [
    // UserService
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
