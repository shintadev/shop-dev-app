package com.shintadev.shop_dev_app.common;

public interface SoftDeletable {

  void setDeleted(boolean deleted);

  boolean isDeleted();
}
