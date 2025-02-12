package com.shintadev.shop_dev_app.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.shintadev.shop_dev_app.util.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final HandlerExceptionResolver handlerExceptionResolver;

  private final JwtTokenProvider jwtTokenProvider;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      // Extract the token from the request
      final String token = jwtTokenProvider.resolveToken(request);
      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      // Extract the username from the token
      final String username = jwtTokenProvider.extractUsername(token);

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      // Check if the username is not null and the user is not already authenticated
      if (username != null && authentication == null) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validate the token
        if (jwtTokenProvider.validateToken(token, userDetails)) {
          final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());

          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      log.error("Error while processing authentication", e);
      handlerExceptionResolver.resolveException(request, response, null, e);
    }
  }
}
