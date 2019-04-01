import { Component, Input } from '@angular/core';
import { FormArray } from '@angular/forms';


@Component({
  selector: 'app-pizza-image',
  templateUrl: './pizza-image.component.html',
  styleUrls: ['./pizza-image.component.css']
})
export class PizzaImageComponent {

  @Input() pizzas: FormArray;
  @Input() toppingNameFn: (id) => string;
  @Input() selectedPizza: number;
}
