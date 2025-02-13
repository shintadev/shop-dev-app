package com.shintadev.shop_dev_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shintadev.shop_dev_app.exception.base.BaseException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends BaseException {
  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(resourceName + " not found with " + fieldName + ": " + fieldValue, "RESOURCE_NOT_FOUND");
  }
}
