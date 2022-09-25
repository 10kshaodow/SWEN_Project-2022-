import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AllInventoryComponent } from './components/all-inventory/all-inventory.component';
import { DeleteInventoryComponent } from './components/delete-inventory/delete-inventory.component';

@NgModule({
  declarations: [AppComponent, AllInventoryComponent, DeleteInventoryComponent],
  imports: [BrowserModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
