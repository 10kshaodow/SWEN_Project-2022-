import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/services/order/order.service';
import { Order } from 'src/app/types/Order';
 
@Component({
 selector: 'app-manage-orders',
 templateUrl: './manage-orders.component.html',
 styleUrls: ['./manage-orders.component.css']
})
export class ManageOrdersComponent implements OnInit {
 allOrder: Order[] = [];
 selectedOrder ?: Order = undefined;
 OrderIsSelected: boolean = false;
 showImmutableUsernameMessage: boolean = false;
 
 
 constructor(private orderService: OrderService) { }
 
 ngOnInit(): void {
   this.reloadOrders();
 }
 orderSelected(order: Order) {
   this.selectedOrder = order;
   this.OrderIsSelected = true;
 }
 
 saveOrder() {
   this.orderService.updateOrder(this.selectedOrder!).subscribe(order => {
     // display a message
     this.reloadOrders();
   });
   this.orderUnselected();
 }

 removeOrder() {
  this.selectedOrder = undefined;
  this.OrderIsSelected = false;
 }
 
 orderUnselected() {
   this.OrderIsSelected = false;
   this.selectedOrder = undefined;
 }
 cancelOrder() {
   this.reloadOrders();
   this.orderUnselected();
 }

 getDateString(order: Order): string {
   let date = new Date(order.orderDate);
  return date.toDateString();
 }
 
 deleteUser() {
   this.orderService.deleteOrder(this.selectedOrder!.id).subscribe(success =>{
     if(success) {
       // need a success message
     } else {
       // need a failure message
     }
     this.reloadOrders();
   });
   this.orderUnselected();
 }
 reloadOrders() {
  
   this.orderService.getAllOrders().subscribe(orders => {
     this.allOrder = orders;
     console.log("got "+orders.length+" orders")
   });
 }
 
 
}
