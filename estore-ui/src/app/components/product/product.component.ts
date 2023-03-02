import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  // Product Component Must be called with a product passed into it
  @Input() product!: Product;
  @Input() clickable: boolean = false;
  @Input() deleteBtn?: boolean;
  @Output() onDeleteClick: EventEmitter<void> = new EventEmitter<void>();
  @Output() cardClick: EventEmitter<void> = new EventEmitter<void>();

  constructor() {}

  ngOnInit(): void {}

  // send event to the parent component handler
  deleteButtonClicked() {
    this.onDeleteClick.emit();
  }

  cardClicked() {
    this.cardClick.emit();
  }
}
