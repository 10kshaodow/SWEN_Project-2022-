import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/types/Product';

@Component({
  selector: 'app-detail-header',
  templateUrl: './detail-header.component.html',
  styleUrls: ['./detail-header.component.css'],
})
export class DetailHeaderComponent implements OnInit {
  @Input() name!: string;
  @Input() price!: string;
  @Input() description!: string;
  @Input() source?: string;
  @Input() quantity?: string;

  constructor() {}

  ngOnInit(): void {}
}
