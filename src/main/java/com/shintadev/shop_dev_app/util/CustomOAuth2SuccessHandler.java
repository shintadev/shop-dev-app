package com.shintadev.shop_dev_app.util;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.domain.dto.response.auth.LoginResponse;
import com.shintadev.shop_dev_app.domain.enums.user.UserRole;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.repository.user.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final UserRepo userRepo;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

    String email = oAuth2User.getAttribute("email");
    if (email == null) {
      throw new IllegalArgumentException("Email is required");
    }
    String name = oAuth2User.getAttribute("name");
    String picture = oAuth2User.getAttribute("picture");
    String givenName = oAuth2User.getAttribute("given_name");
    String familyName = oAuth2User.getAttribute("family_name");

    User user = userRepo.findByEmail(email)
        .orElseGet(() -> {
          String encodedPassword = passwordEncoder
              .encode(email.subSequence(0, email.indexOf('@')));
          User newUser = User.builder()
              .email(email)
              .password(encodedPassword)
              .displayName(name)
              .firstName(givenName)
              .lastName(familyName)
              .avatarUrl(picture)
              .role(UserRole.ROLE_USER)
              .status(UserStatus.ACTIVE)
              .build();
          return userRepo.saveAndFlush(newUser);
        });

    String token = jwtTokenProvider.createToken(user);

    response.addHeader("Authorization", "Bearer " + token);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(new ObjectMapper()
        .writeValueAsString(
            LoginResponse.builder()
                .token(token)
                .expiresInMs(jwtTokenProvider.getValidityInMilliseconds())
                .build()));
  }
}
