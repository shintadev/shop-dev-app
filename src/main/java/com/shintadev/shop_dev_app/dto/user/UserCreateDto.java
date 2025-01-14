package com.shintadev.shop_dev_app.dto.user;

import lombok.Data;

@Data
public class UserCreateDto extends UserDto {
  private String username;
  private String email;
  private String password;
}
