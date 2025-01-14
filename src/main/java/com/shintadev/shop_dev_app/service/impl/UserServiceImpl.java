package com.shintadev.shop_dev_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.dto.user.UserDto;
import com.shintadev.shop_dev_app.model.User;
import com.shintadev.shop_dev_app.repository.UserRepo;
import com.shintadev.shop_dev_app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public User create(UserDto userDto) {
    User user = objectMapper.convertValue(userDto, User.class);
    return userRepo.saveAndFlush(user);
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return userRepo.findAll(pageable);
  }

  @Override
  public User findOne(Long id) {
    return userRepo.findById(id).orElse(null);
  }

  @Override
  public User findByUsername(String username) {
    return userRepo.findByUsername(username);
  }

  @Override
  public User update(Long id, UserDto userDto) {
    return userRepo.saveAndFlush(objectMapper.convertValue(userDto, User.class));
  }

  @Override
  public void delete(Long id) {
    userRepo.deleteById(id);
  }

  @Override
  public boolean isExists(Long id) {
    return userRepo.existsById(id);
  }

  @Override
  public User findByEmail(String email) {
    return userRepo.findByEmail(email);
  }

}
