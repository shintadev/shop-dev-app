package com.shintadev.shop_dev_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.repository.user.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepo userRepo;

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder() {

      // Uncomment this block to use plain text password
      // @Override
      // public String encode(CharSequence rawPassword) {
      // return rawPassword.toString();
      // }

      // @Override
      // public boolean matches(CharSequence rawPassword, String encodedPassword) {
      // return rawPassword.toString().equals(encodedPassword);
      // }
    };
  }

  @Bean
  UserDetailsService userDetailsService() {
    return email -> userRepo.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());

    return provider;
  }
}
