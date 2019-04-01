package com.nortal.pizzastore.auth;

import com.nortal.pizzastore.usecase.registeruser.Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class PasswordEncoderImpl implements Encoder, PasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {

    // TODO! Proper password encoding

    return rawPassword.toString();
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {

    // TODO! Proper password encoding

    return rawPassword.toString().equals(encodedPassword);
  }
}
