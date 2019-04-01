import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Product } from '../../../@shared/product';

@Component({
  selector: 'app-pizza-creation',
  templateUrl: './pizza-creation.component.html',
  styleUrls: ['./pizza-creation.component.css']
})
export class PizzaCreationComponent {

  @Input() pizzasControls: FormGroup[];
  @Input() bases: Product[];
  @Input() toppings: Product[];
  @Input() selectedPizza: number;

  @Output() add = new EventEmitter();
  @Output() select = new EventEmitter<number>();
  @Output() submit = new EventEmitter();

  addPizza() {
    this.add.emit();
    this.selectedPizza = this.pizzasControls.length - 1;
    this.select.emit(this.selectedPizza);
  }

  selectPizza(index) {
    this.selectedPizza = this.selectedPizza === index ? -1 : index;
    this.select.emit(index);
  }
}
