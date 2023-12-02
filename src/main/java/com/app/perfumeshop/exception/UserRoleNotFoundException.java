package com.app.perfumeshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserRoleNotFoundException extends RuntimeException {
  public UserRoleNotFoundException(String message) {
    super(message);
  }
}
