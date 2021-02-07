import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CalcParamsComponent } from './calculator/calc-params/calc-params.component';
import { CalcOutputComponent } from './calculator/calc-output/calc-output.component';
import { CalcSliderComponent } from './calculator/calc-slider/calc-slider.component';
import { CalcInputComponent } from './calculator/calc-input/calc-input.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    CalcParamsComponent, CalcOutputComponent, CalcSliderComponent, CalcInputComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
