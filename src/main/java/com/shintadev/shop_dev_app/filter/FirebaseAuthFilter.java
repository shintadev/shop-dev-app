package com.shintadev.shop_dev_app.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {

  private final FirebaseAuth firebaseAuth;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String bearerToken = request.getHeader("Authorization");

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      try {
        String token = bearerToken.substring(7);
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(token);

        UserDetails userDetails = createUserDetails(decodedToken);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        log.error("Error verifying Firebase token", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private UserDetails createUserDetails(FirebaseToken token) {
    return User.builder()
        .username(token.getEmail())
        .password("")
        .roles("USER")
        .authorities(extractAuthorities(token))
        .build();
  }

  private Collection<? extends GrantedAuthority> extractAuthorities(FirebaseToken token) {
    List<String> roles = new ArrayList<>();

    if (Boolean.TRUE.equals(token.getClaims().get("admin"))) {
      roles.add("ROLE_ADMIN");
    } else {
      roles.add("ROLE_USER");
    }

    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
