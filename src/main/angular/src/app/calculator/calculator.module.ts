import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalcParamsComponent } from './calc-params/calc-params.component';
import { CalcOutputComponent } from './calc-output/calc-output.component';



@NgModule({
  declarations: [CalcParamsComponent, CalcOutputComponent],
  imports: [
    CommonModule
  ]
})
export class CalculatorModule { }
