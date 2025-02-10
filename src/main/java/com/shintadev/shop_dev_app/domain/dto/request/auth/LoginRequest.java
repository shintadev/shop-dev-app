package com.shintadev.shop_dev_app.domain.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
  private String password;
}
