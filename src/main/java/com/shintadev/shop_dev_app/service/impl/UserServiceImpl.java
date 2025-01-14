package com.shintadev.shop_dev_app.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.model.User;
import com.shintadev.shop_dev_app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public User save(User entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public User findById(BigDecimal id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public User findByUsername(String username) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
  }

}
