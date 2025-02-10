package com.shintadev.shop_dev_app.domain.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyRequest {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 6, max = 6, message = "Verification code must be 6 digit code")
  private String verificationCode;
}
