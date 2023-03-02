import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './screens/home/home.component';
import { UserSignInComponent } from './screens/user-sign-in/user-sign-in.component';
import { CartComponent } from './screens/cart/cart.component';
import { AdminComponent } from './screens/admin/admin.component';
import { CreateProductComponent } from './screens/create-product/create-product.component';
import { CheckoutComponent } from './screens/checkout/checkout.component';
import { OrderComponent } from './screens/order/order.component';
import { ProductPageComponent } from './screens/product-page/product-page.component';
import { OrnithologyDashboardComponent } from './screens/ornithology-dashboard/ornithology-dashboard.component';
import { OrnithologistFalconViewComponent } from './screens/ornithologist-falcon-view/ornithologist-falcon-view.component';
import { CreateHealthReportComponent } from './screens/create-health-report/create-health-report.component';
import { AllNotificationsComponent } from './screens/all-notifications/all-notifications.component';
import { MyFalconsComponent } from './screens/my-falcons/my-falcons.component';
import { MyFalconViewComponent } from './screens/my-falcon-view/my-falcon-view.component';
import { ViewHealthReportComponent } from './screens/view-health-report/view-health-report.component';

import { LandingComponent } from './screens/landing/landing.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: LandingComponent },
  { path: 'home', component: HomeComponent },
  { path: 'product/:id', component: ProductPageComponent },
  { path: 'login', component: UserSignInComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'notifications', component: AllNotificationsComponent },
  { path: 'sponsorings', component: MyFalconsComponent },
  { path: 'cart', component: CartComponent },
  { path: 'order/:id', component: OrderComponent },
  { path: 'report/:id', component: ViewHealthReportComponent },
  { path: 'create/report/:falconID', component: CreateHealthReportComponent },
  {
    path: 'create/report/:falconID/:reportID',
    component: CreateHealthReportComponent,
  },
  // { path: 'checkout', component: CheckoutComponent },
  // { path: 'create-product', component: CreateProductComponent },
  // { path: 'create-product/:id', component: CreateProductComponent },
  // { path: 'ornithology-dashboard', component: OrnithologyDashboardComponent },
  // { path: 'ornithology-view/:id', component: OrnithologistFalconViewComponent},
  // {path: 'my-falcon/:id', component: MyFalconViewComponent},
  // {
  //   path: 'create-health-report/:falconID',
  //   component: CreateHealthReportComponent,
  // },
  // {
  //   path: 'create-health-report/:falconID/:reportID',
  //   component: CreateHealthReportComponent,
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRouting {}
