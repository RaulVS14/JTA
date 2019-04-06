package com.nortal.pizzastore.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
public class Order {
  private Long id;
  private Customer customer;
  @Builder.Default
  private LocalDateTime created = LocalDateTime.now();
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();

  public BigDecimal getTotalPrice() {
    BigDecimal totalPrice = new BigDecimal(0);
    for (OrderItem item : items) {
      if (item != null && item.getPrice() != null && (item.getPrice() instanceof BigDecimal)) {
        totalPrice = totalPrice.add(item.getPrice());
      }
    }
    return totalPrice;
  }
}
