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
    String pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9._%+-]+\\.[a-zA-Z]{2,}";
    boolean verifyName = name == null || name.trim().length() == 0;
    boolean verifyEmail = email == null || email.trim().length() == 0 || !email.matches(pattern);
    boolean verifyPhone = phone == null || phone.trim().length() == 0;
    boolean verifyAddress = address == null || address.trim().length() == 0;
    if (verifyName || verifyEmail || verifyPhone || verifyAddress ){
      throw new InvalidCustomer("Invalid form submitted!");
    }

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
