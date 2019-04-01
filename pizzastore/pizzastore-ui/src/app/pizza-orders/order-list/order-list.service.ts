import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PizzaOrder } from '../new-order/pizza-order';
import { OrderSearchResult } from './order-search-result';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderListService {

  private readonly url = '/api/orders';

  constructor(private readonly http: HttpClient) {
  }

  placeOrder(order: PizzaOrder) {
    return this.http.post(this.url, order);
  }

  getOrders(): Observable<OrderSearchResult[]> {
    return this.http.get<OrderSearchResult[]>(this.url);
  }

  cancelOrder(id: number) {
    return this.http.delete(this.url + '/' + id);
  }
}
