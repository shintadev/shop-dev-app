package com.shintadev.shop_dev_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.filter.JwtAuthFilter;
import com.shintadev.shop_dev_app.repository.user.UserRepo;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationProvider authenticationProvider;

  private final JwtAuthFilter firebaseAuthFilter;

  private final UserRepo userRepo;

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder() {
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

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(firebaseAuthFilter,
            UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(authorize -> authorize
            // Public endpoints
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/products/**").permitAll()

            // Secured endpoints
            .requestMatchers("/api/users/**").authenticated()
            .requestMatchers("/api/orders/**").authenticated()

            // Admin-only endpoints
            .requestMatchers("/api/admin/**").hasRole("ROLE_ADMIN"))
        .oauth2Login(
            oauth2 -> oauth2
                .successHandler(null));

    return httpSecurity.build();
  }
}
