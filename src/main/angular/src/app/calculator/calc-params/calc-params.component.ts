import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-calc-params',
  templateUrl: './calc-params.component.html',
  styleUrls: ['./calc-params.component.scss']
})
export class CalcParamsComponent implements OnInit {

  params = [
    {
      name: "dayStart",
      label: "Day Start",
      min: 0,
      max: 100
    },
    {
      name: "interval",
      label: "Interval",
      min: 0,
      max: 48
    },
    {
      name: "numOfPayments",
      label: "# Payments",
      min: 0,
      max: 48
    },
    {
      name: "momentBuy",
      label: "Moment Buy",
      min: 0,
      max: 5
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
