package com.shintadev.shop_dev_app.repository;

import com.shintadev.shop_dev_app.model.User;

public interface UserRepo {
  User findByUsername(String username);

}
