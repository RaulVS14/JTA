import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router';
import { PizzaOrderComponent } from '../pizza-orders/new-order/pizza-order.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './auth-guard.service';
import { LogInComponent } from './log-in/log-in.component';
import { OrderListComponent } from '../pizza-orders/order-list/order-list.component';

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LogInComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'orders', component: OrderListComponent, canActivate: [AuthGuardService]},
  {path: 'new-order', component: PizzaOrderComponent, canActivate: [AuthGuardService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule {}
