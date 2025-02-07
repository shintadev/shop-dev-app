package com.shintadev.shop_dev_app.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.EqualsAndHashCode;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@EqualsAndHashCode(callSuper = false)
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    String message = fieldName + " not found with " + fieldName + ": " + fieldValue;
    throw new RuntimeException(message);
  }
}
