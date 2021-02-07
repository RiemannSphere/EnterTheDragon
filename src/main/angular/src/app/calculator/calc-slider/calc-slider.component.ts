import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-calc-slider',
  templateUrl: './calc-slider.component.html',
  styleUrls: ['./calc-slider.component.scss']
})
export class CalcSliderComponent implements OnInit {

  @Input() label: string;
  @Input() min: number;
  @Input() max: number;
  value: number;

  constructor() { }

  ngOnInit(): void {
  }

}
