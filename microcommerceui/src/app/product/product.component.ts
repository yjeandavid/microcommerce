import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
  @Input() id: number;
  @Input() name: string;
  @Input() price: number;
  @Input() purchasePrice: number;

  @Input() isAuth: boolean;
  
  constructor() { }

  ngOnInit() {
  }

}
