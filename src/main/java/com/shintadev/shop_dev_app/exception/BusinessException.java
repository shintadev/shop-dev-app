package com.shintadev.shop_dev_app.exception;

import com.shintadev.shop_dev_app.exception.base.BaseException;

public class BusinessException extends BaseException {
  public BusinessException(String message) {
    super(message, "BUSINESS_ERROR");
  }

  public BusinessException(String message, String code) {
    super(message, code);
  }
}