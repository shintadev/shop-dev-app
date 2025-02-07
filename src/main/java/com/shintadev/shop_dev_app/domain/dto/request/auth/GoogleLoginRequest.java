package com.shintadev.shop_dev_app.domain.dto.request.auth;

import lombok.Data;

@Data
public class GoogleLoginRequest {

  private String idToken;
}
