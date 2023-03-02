import { Component, Input, OnInit } from '@angular/core';
import { Order } from 'src/app/types/Order';

@Component({
  selector: 'app-order-snippet',
  templateUrl: './order-snippet.component.html',
  styleUrls: ['./order-snippet.component.css'],
})
export class OrderSnippetComponent implements OnInit {
  @Input() order!: Order;

  constructor() {}

  ngOnInit(): void {}
}
