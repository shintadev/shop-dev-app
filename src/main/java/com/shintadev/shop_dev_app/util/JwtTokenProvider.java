package com.shintadev.shop_dev_app.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Getter
public class JwtTokenProvider {

  @Value("${security.jwt.secret}")
  private String secret;

  @Value("${security.jwt.expiration-in-ms}")
  private long validityInMilliseconds;

  private SecretKey secretKey;

  @PostConstruct
  protected void init() {
    try {
      log.info("Initializing JwtTokenProvider with secret: {}", secret);
      this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      log.error("Failed to initialize JwtTokenProvider", e);
      throw e;
    }
  }

  public String createToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    long now = System.currentTimeMillis();

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(now))
        .setExpiration(new Date(now + validityInMilliseconds))
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String extractUsername(String token) {
    return extractClaims(token).getSubject();
  }

  public Date extractExpiration(String token) {
    return extractClaims(token).getExpiration();
  }

  public Claims extractClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException e) {
      throw new JwtException("Invalid token");
    }
  }
}
