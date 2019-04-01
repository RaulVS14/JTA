import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';


@Component({
  selector: 'app-pizza-summary',
  templateUrl: './pizza-summary.component.html',
  styleUrls: ['./pizza-summary.component.css']
})
export class PizzaSummaryComponent {

  @Input() form: FormGroup;
  @Input() total: number;

  @Input() baseDisplayFn: (id) => string;
  @Input() toppingDisplayFn: (id) => string;

  @Input() basePriceFn: (id) => number;
  @Input() toppingPriceFn: (id) => number;

  @Output() back = new EventEmitter();

  get customerControl() {
    return this.form.get('customer');
  }

  get pizzasControl() {
    return this.form.get('pizzas');
  }
}
