import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogInComponent } from './log-in/log-in.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreRoutingModule } from './core-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { PizzaOrdersModule } from '../pizza-orders/pizza-orders.module';
import { JwtModule } from '@auth0/angular-jwt';
import { AlertComponent } from '../@shared/alert/alert.component';

@NgModule({
  declarations: [
    LogInComponent,
    RegisterComponent,
    NavBarComponent,
    AlertComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    PizzaOrdersModule,
    CoreRoutingModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => {
          return localStorage.getItem('access_token');
        },
        whitelistedDomains: ['localhost:8080']
      }
    })
  ],
  exports: [
    AlertComponent,
    NavBarComponent
  ]
})
export class CoreModule {
}
