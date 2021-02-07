import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcParamsComponent } from './calc-params.component';

describe('CalcParamsComponent', () => {
  let component: CalcParamsComponent;
  let fixture: ComponentFixture<CalcParamsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CalcParamsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CalcParamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
