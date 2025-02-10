package com.shintadev.shop_dev_app.service;

import com.shintadev.shop_dev_app.domain.dto.request.auth.LoginRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.PasswordResetRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.RegisterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.VerifyRequest;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.dto.response.auth.LoginResponse;

public interface AuthService {

  UserResponse register(RegisterRequest request);

  void verify(VerifyRequest request);

  void resendVerification(String email);

  LoginResponse login(LoginRequest request);

  void sendPasswordReset(String email);

  void verifyPasswordReset(PasswordResetRequest request);
}
