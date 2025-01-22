package com.shintadev.shop_dev_app.service;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.payload.user.UserDto;

public interface UserService extends BaseService<UserDto, Long, UserDto, UserDto> {

  UserDto findByEmail(String email);

}
