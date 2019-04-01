import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder } from '@angular/forms';
import { Product } from '../../@shared/product';
import { ProductService } from './product.service';
import { Pizza, PizzaOrder } from './pizza-order';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from '../../@core/auth.service';
import { OrderListService } from '../order-list/order-list.service';
import { Customer } from '../../@shared/customer';

@Component({
  selector: 'app-pizza-order',
  templateUrl: './pizza-order.component.html',
  styleUrls: ['./pizza-order.component.css']
})
export class PizzaOrderComponent implements OnInit {

  total: number;
  selectedPizza = 0;
  pizzaCreating = true;
  pizzaBases: Product[];
  toppings: Product[];

  form = this.fb.group({
    customer: this.fb.group({  // TODO Add validations for customer details
      name: [''],
      email: [''],
      phone: [''],
      address: ['']
    }),
    pizzas: this.fb.array([
      this.createPizzasFormGroup()
    ])
  });

  constructor(private fb: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private productService: ProductService,
              private orderService: OrderListService) {
  }

  ngOnInit(): void {
    const bases$ = this.productService.getProducts('PIZZA_BASE');
    const toppings$ = this.productService.getProducts('PIZZA_TOPPING');

    forkJoin(bases$, toppings$).subscribe(([bases, toppings]) => {
      this.pizzaBases = bases;
      this.toppings = toppings;
      this.calculateTotal(this.getPizzasFormArray().value);
      this.getPizzasFormArray().valueChanges.subscribe(value => this.calculateTotal(value));
    }, error => {
      if (error.status === 403) {
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    });
  }

  getBaseLabel = id => {
    const base = this.pizzaBases.find(b => b.id === id);
    return base ? base.name : '';
  };

  getToppingLabel = id => {
    const topping = this.toppings.find(t => t.id === id);
    return topping ? topping.name : '';
  };

  getBasePrice = id => {
    const base = this.pizzaBases.find(b => b.id === id);
    return base ? base.price : null;
  };

  getToppingPrice = id => {
    const topping = this.toppings.find(t => t.id === id);
    return topping ? topping.price : null;
  };

  createPizzasFormGroup() {
    return this.fb.group({
      base: [1],
      toppings: [[]]
    });
  }

  getPizzasFormArray(): FormArray {
    const pizzasKey = 'pizzas';
    return this.form.get(pizzasKey) as FormArray;
  }

  addPizzaControl() {
    const controls = this.getPizzasFormArray();
    controls.push(this.createPizzasFormGroup());
  }

  getPizzasControls() {
    const pizzas = this.getPizzasFormArray();
    return pizzas.controls;
  }

  submitOrder() {
    const order = new PizzaOrder();
    order.customer = this.createCustomer();
    order.pizzas = this.createPizzas();
    this.orderService.placeOrder(order).subscribe(() => {
      this.router.navigate(['/orders']);
    });
  }

  private createPizzas(): Pizza[] {
    const pizzas = this.getPizzasFormArray().value;

    const orderPizzas = [];
    for (const p of pizzas) {
      const pizza = new Pizza();
      pizza.base = p.base;
      pizza.toppings = p.toppings;
      orderPizzas.push(pizza);
    }
    return orderPizzas;
  }

  private createCustomer(): Customer {
    const customerControl = this.form.get('customer');

    const customer = new Customer();
    customer.name = customerControl.get('name').value;
    customer.email = customerControl.get('email').value;
    customer.phone = customerControl.get('phone').value;
    customer.address = customerControl.get('address').value;

    return customer;
  }

  selectPizza(index: number) {
    this.selectedPizza = index;
  }

  private calculateTotal(pizzas) {
    let price = 0;
    for (const pizza of pizzas) {
      price += +this.getBasePrice(pizza.base);
      for (const topping of pizza.toppings) {
        price += +this.getToppingPrice(topping);
      }
    }
    this.total = price;
  }
}
