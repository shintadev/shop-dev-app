package com.shintadev.shop_dev_app.service;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.dto.user.UserDto;
import com.shintadev.shop_dev_app.model.User;

public interface UserService extends BaseService<User, Long, UserDto, UserDto> {

  User findByUsername(String username);

  User findByEmail(String email);

}
