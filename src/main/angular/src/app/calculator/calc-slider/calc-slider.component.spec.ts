import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcSliderComponent } from './calc-slider.component';

describe('CalcSliderComponent', () => {
  let component: CalcSliderComponent;
  let fixture: ComponentFixture<CalcSliderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalcSliderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalcSliderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
