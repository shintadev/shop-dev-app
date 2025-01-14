package com.shintadev.shop_dev_app.service;

import java.math.BigDecimal;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.model.User;

public interface UserService extends BaseService<User, BigDecimal> {
  User findByUsername(String username);

}
