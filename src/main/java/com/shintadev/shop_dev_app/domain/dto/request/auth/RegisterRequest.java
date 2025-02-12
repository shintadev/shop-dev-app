package com.shintadev.shop_dev_app.domain.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
  private String password;

  @NotBlank
  @Size(min = 2, max = 64, message = "First name must be between 2 and 64 characters")
  private String firstName;

  @NotBlank
  @Size(min = 2, max = 64, message = "Last name must be between 2 and 64 characters")
  private String lastName;

  @Size(max = 128, message = "Display name must not exceed 128 characters")
  private String displayName;

  @Size(max = 16, message = "Phone number must not exceed 16 characters")
  private String phone;
}
