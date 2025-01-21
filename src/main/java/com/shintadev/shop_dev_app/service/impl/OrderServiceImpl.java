package com.shintadev.shop_dev_app.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.dto.order.OrderDto;
import com.shintadev.shop_dev_app.model.Order;
import com.shintadev.shop_dev_app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

  @Override
  public Order create(OrderDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Page<Order> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Order findOne(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findOne'");
  }

  @Override
  public Order update(Long id, OrderDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public boolean isExists(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isExists'");
  }

}
