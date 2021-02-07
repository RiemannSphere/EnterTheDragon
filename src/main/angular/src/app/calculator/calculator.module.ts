import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalcParamsComponent } from './calc-params/calc-params.component';
import { CalcOutputComponent } from './calc-output/calc-output.component';
import { CalcSliderComponent } from './calc-slider/calc-slider.component';
import { CalcInputComponent } from './calc-input/calc-input.component';


@NgModule({
  declarations: [CalcParamsComponent, CalcOutputComponent, CalcSliderComponent, CalcInputComponent],
  imports: [
    CommonModule
  ],
  exports: [CalcParamsComponent, CalcOutputComponent, CalcSliderComponent, CalcInputComponent]
})
export class CalculatorModule { }
