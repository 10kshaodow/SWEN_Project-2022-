import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllInventoryComponent } from './components/all-inventory/all-inventory.component';
import { CreateProductComponent } from './components/create-product/create-product.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';


const routes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: 'products', component: AllInventoryComponent },
  { path: 'create', component: CreateProductComponent },
  { path: 'update', component: UpdateProductComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }