import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PizzaOrderComponent } from './new-order/pizza-order.component';
import { PizzaToppingsComponent } from './new-order/pizza-toppings/pizza-toppings.component';
import { PizzaSummaryComponent } from './new-order/pizza-summary/pizza-summary.component';
import { PizzaImageComponent } from './new-order/pizza-image/pizza-image.component';
import { PizzaCreationComponent } from './new-order/pizza-creation/pizza-creation.component';
import { PizzaBaseComponent } from './new-order/pizza-base/pizza-base.component';
import { FormatDatePipe } from '../@shared/pipes/format-date.pipe';
import { OrderListComponent } from './order-list/order-list.component';
import { CustomerDetailsComponent } from './new-order/pizza-summary/customer-details/customer-details.component';

@NgModule({
  declarations: [
    FormatDatePipe,
    PizzaOrderComponent,
    PizzaBaseComponent,
    PizzaToppingsComponent,
    PizzaSummaryComponent,
    PizzaImageComponent,
    PizzaCreationComponent,
    OrderListComponent,
    CustomerDetailsComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ]
})
export class PizzaOrdersModule {
}
