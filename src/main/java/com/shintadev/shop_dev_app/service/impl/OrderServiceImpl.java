package com.shintadev.shop_dev_app.service.impl;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.model.Order;
import com.shintadev.shop_dev_app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

  @Override
  public Order save(Order entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public Order findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Order findByOrderNumber(String orderNumber) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByOrderNumber'");
  }

}
