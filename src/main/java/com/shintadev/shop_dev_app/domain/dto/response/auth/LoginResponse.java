package com.shintadev.shop_dev_app.domain.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

  private String token;

  private long expiresInMs;
}
