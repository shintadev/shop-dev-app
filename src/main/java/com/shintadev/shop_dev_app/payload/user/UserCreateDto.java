package com.shintadev.shop_dev_app.payload.user;

import lombok.Data;

@Data
public class UserCreateDto {
  private String username;
  private String email;
  private String password;
}
