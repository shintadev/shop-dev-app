package com.shintadev.shop_dev_app.service.impl;

import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.repository.user.UserRepo;
import com.shintadev.shop_dev_app.service.AuthService;
import com.shintadev.shop_dev_app.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  // private final UserRepo userRepo;

  // private final JwtTokenProvider jwtTokenProvider;

  // private final FirebaseAuth firebaseAuth;

  // @Override
  // public String authenticateGoogleUser(String idToken) {
  // try {
  // FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
  // String uid = decodedToken.getUid();
  // String email = decodedToken.getEmail();
  // String name = decodedToken.getName();

  // // Check if user exists in database; if not, create a new user
  // User user = userRepo.findByEmail(email)
  // .orElseGet(() -> {
  // User newUser = User.builder()
  // .email(email)
  // // .name(name)
  // // .roles(Arrays.asList("ROLE_USER"))
  // .build();
  // return userRepo.save(newUser);
  // });

  // // Generate JWT token for user
  // return jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
  // } catch (Exception e) {
  // throw new RuntimeException("Invalid token" + e.getMessage());
  // }
  // }

  // @Override
  // public User getCurrentUser() {
  // String email =
  // SecurityContextHolder.getContext().getAuthentication().getName();
  // return userRepo.findByEmail(email)
  // .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
  // }
}
