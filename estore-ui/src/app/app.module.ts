import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AllInventoryComponent } from './components/all-inventory/all-inventory.component';
import { SearchProductsComponent } from './components/search-products/search-products.component';

@NgModule({
  declarations: [AppComponent, AllInventoryComponent, SearchProductsComponent],
  imports: [BrowserModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
