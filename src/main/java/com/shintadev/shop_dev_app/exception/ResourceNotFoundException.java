package com.shintadev.shop_dev_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    String message = fieldName + " not found with " + fieldName + ": " + fieldValue;
    throw new RuntimeException(message);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
