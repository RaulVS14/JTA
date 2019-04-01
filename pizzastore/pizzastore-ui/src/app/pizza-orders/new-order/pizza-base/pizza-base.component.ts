import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Product } from '../../../@shared/product';
import { v4 as uuid } from 'uuid';

export const PIZZA_BASE_ACCESSOR = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => PizzaBaseComponent),
  multi: true
};

@Component({
  selector: 'app-pizza-base',
  providers: [PIZZA_BASE_ACCESSOR],
  templateUrl: './pizza-base.component.html',
  styleUrls: ['./pizza-base.component.css']
})
export class PizzaBaseComponent implements ControlValueAccessor {

  @Input() bases: Product[];

  uuid = uuid();

  private onModelChange: any;
  private onTouch: any;

  value: number;
  focused: number;

  registerOnChange(fn: any) {
    this.onModelChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouch = fn;
  }

  writeValue(value: number) {
    this.value = value;
  }

  onChange(value: number) {
    this.value = value;
    this.onModelChange(value);
  }

  onFocus(value: number) {
    this.focused = value;
    this.onTouch();
  }
}
