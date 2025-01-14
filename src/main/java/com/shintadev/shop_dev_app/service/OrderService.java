package com.shintadev.shop_dev_app.service;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.model.Order;

public interface OrderService extends BaseService<Order, Long> {
  public Order findByOrderNumber(String orderNumber);

}
