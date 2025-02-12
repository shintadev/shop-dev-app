package com.shintadev.shop_dev_app.domain.dto.request.auth;

import lombok.Data;

@Data
public class PasswordResetRequest {

  private String email;

  private String token;

  private String password;
}
