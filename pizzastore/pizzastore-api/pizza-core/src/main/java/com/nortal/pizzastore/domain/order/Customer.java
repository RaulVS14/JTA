package com.nortal.pizzastore.domain.order;

import com.nortal.pizzastore.domain.user.User;
import lombok.Builder;
import lombok.Value;

@Value
public class Customer {
  private String name;
  private String email;
  private String phone;
  private String address;
  private User user;

  @Builder
  private Customer(String name, String email,
                   String phone, String address, User user) {

    // TODO! Validations - use the exception below

    this.name = name;
    this.email = email;
    this.phone = phone;
    this.address = address;
    this.user = user;
  }

  class InvalidCustomer extends InvalidOrder {
    public InvalidCustomer(String message) {
      super(message);
    }
  }
}
