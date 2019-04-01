import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Product } from '../../../@shared/product';

const PIZZA_TOPPINGS_ACCESSOR = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => PizzaToppingsComponent),
  multi: true
};

@Component({
  selector: 'app-pizza-toppings',
  providers: [PIZZA_TOPPINGS_ACCESSOR],
  templateUrl: './pizza-toppings.component.html',
  styleUrls: ['./pizza-toppings.component.css']
})
export class PizzaToppingsComponent implements ControlValueAccessor {

  @Input() toppings: Product[];

  value: number[] = [];

  private onTouch: any;
  private onModelChange: any;

  registerOnChange(fn) {
    this.onModelChange = fn;
  }

  registerOnTouched(fn) {
    this.onTouch = fn;
  }

  writeValue(value) {
    this.value = value;
  }

  updateTopping(topping: number) {
    if (this.value.includes(topping)) {
      this.value = this.value.filter(x => topping !== x);
    } else {
      this.value.push(topping);
    }
    this.onModelChange(this.value);
  }
}
