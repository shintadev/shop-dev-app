package com.shintadev.shop_dev_app.service.impl;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.domain.dto.request.auth.LoginRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.PasswordResetRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.RegisterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.VerifyRequest;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.dto.response.auth.LoginResponse;
import com.shintadev.shop_dev_app.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  @Override
  public UserResponse getCurrentUser() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getCurrentUser'");
  }

  @Override
  public LoginResponse register(RegisterRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'register'");
  }

  @Override
  public void verify(VerifyRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'verify'");
  }

  @Override
  public void resendVerification(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resendVerification'");
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'login'");
  }

  @Override
  public void sendPasswordReset(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'sendPasswordReset'");
  }

  @Override
  public void verifyPasswordReset(PasswordResetRequest request) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'verifyPasswordReset'");
  }

}
