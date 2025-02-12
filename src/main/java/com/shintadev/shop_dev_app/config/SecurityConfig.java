package com.shintadev.shop_dev_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shintadev.shop_dev_app.filter.JwtAuthFilter;
import com.shintadev.shop_dev_app.util.CustomOAuth2SuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthFilter firebaseAuthFilter;

  private final AuthenticationProvider authenticationProvider;

  private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
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
            .requestMatchers("/api/admin/**").hasRole("ADMIN")

            // All other requests must be authenticated
            .anyRequest().authenticated())
        .oauth2Login(
            oauth2 -> oauth2
                .successHandler(customOAuth2SuccessHandler));

    return httpSecurity.build();
  }
}
