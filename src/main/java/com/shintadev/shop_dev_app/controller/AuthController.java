package com.shintadev.shop_dev_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.auth.LoginRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.PasswordResetRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.RegisterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.VerifyRequest;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.dto.response.auth.LoginResponse;
import com.shintadev.shop_dev_app.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponse> signup(@RequestBody RegisterRequest request) {
    return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
  }

  @PostMapping("/verify")
  public ResponseEntity<String> verify(@RequestBody VerifyRequest request) {
    authService.verify(request);
    return new ResponseEntity<>("User verified successfully", HttpStatus.OK);
  }

  @PostMapping("/resend-verification")
  public ResponseEntity<String> resendVerification(@RequestParam String email) {
    authService.resendVerification(email);
    return new ResponseEntity<>("Verification email sent successfully", HttpStatus.OK);
  }

  @PostMapping("/signin")
  public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest request) {
    return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
  }

  @PostMapping("/password-reset")
  public ResponseEntity<String> passwordReset(@RequestParam String email) {
    authService.sendPasswordReset(email);
    return new ResponseEntity<>("Password reset email sent successfully", HttpStatus.OK);
  }

  @PostMapping("/password-reset/verify")
  public ResponseEntity<String> passwordResetVerify(
      @RequestBody PasswordResetRequest request) {
    authService.verifyPasswordReset(request);
    return new ResponseEntity<>("Password reset code verified successfully", HttpStatus.OK);
  }
}
