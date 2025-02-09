package com.shintadev.shop_dev_app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.auth.GoogleLoginRequest;
import com.shintadev.shop_dev_app.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  // @PostMapping("/google")
  // public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request)
  // {
  // String token = authService.authenticateGoogleUser(request.getIdToken());
  // Map<String, String> response = new HashMap<>();
  // response.put("token", token);
  // return ResponseEntity.ok(response);
  // }
}
