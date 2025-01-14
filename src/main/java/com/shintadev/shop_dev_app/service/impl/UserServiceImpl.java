package com.shintadev.shop_dev_app.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.dto.user.UserCreateDto;
import com.shintadev.shop_dev_app.dto.user.UserDto;
import com.shintadev.shop_dev_app.model.User;
import com.shintadev.shop_dev_app.repository.UserRepo;
import com.shintadev.shop_dev_app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public User create(UserDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public List<User> findAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public User findOne(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findOne'");
  }

  @Override
  public UserDto findByUsername(String username) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
  }

  @Override
  public User update(Long id, UserDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

}
