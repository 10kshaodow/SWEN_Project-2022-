import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AllInventoryComponent } from './components/all-inventory/all-inventory.component';
import { DeleteInventoryComponent } from './components/delete-inventory/delete-inventory.component';
import { SearchProductsComponent } from './components/search-products/search-products.component';
import { GetProductComponent } from './components/get-product/get-product.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { CreateProductComponent } from './components/create-product/create-product.component';
import { FormsModule } from '@angular/forms';
import { HomeComponent } from './screens/home/home.component';
import { AppRouting } from './app-routing.module';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    AllInventoryComponent,
    SearchProductsComponent,
    GetProductComponent,
    DeleteInventoryComponent,
    CreateProductComponent,
    UpdateProductComponent,
    HomeComponent,
    NavBarComponent,
  ],
  imports: [BrowserModule, AppRouting, HttpClientModule, FormsModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
