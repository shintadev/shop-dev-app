package com.shintadev.shop_dev_app.service.other.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.auth.LoginRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.PasswordResetRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.RegisterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.auth.VerifyRequest;
import com.shintadev.shop_dev_app.domain.dto.response.auth.LoginResponse;
import com.shintadev.shop_dev_app.domain.dto.response.user.UserResponse;
import com.shintadev.shop_dev_app.domain.enums.user.UserRole;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.cart.Cart;
import com.shintadev.shop_dev_app.domain.model.user.PasswordResetToken;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.domain.model.user.VerifyEmailToken;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.UserMapper;
import com.shintadev.shop_dev_app.repository.user.PasswordResetTokenRepo;
import com.shintadev.shop_dev_app.repository.user.UserRepo;
import com.shintadev.shop_dev_app.repository.user.VerifyEmailTokenRepo;
import com.shintadev.shop_dev_app.service.other.AuthService;
import com.shintadev.shop_dev_app.service.other.EmailService;
import com.shintadev.shop_dev_app.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final UserRepo userRepo;

  private final VerifyEmailTokenRepo verifyEmailTokenRepo;

  private final PasswordResetTokenRepo passwordResetTokenRepo;

  private final EmailService emailService;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider jwtTokenProvider;

  private final AuthenticationManager authenticationManager;

  @Override
  public UserResponse register(RegisterRequest request) {
    // 1. Check if email is already in use
    Optional<User> userOpt = userRepo.findByEmail(request.getEmail());

    if (userOpt.isPresent()) {
      if (userOpt.get().getStatus() == UserStatus.INACTIVE) {
        throw new RuntimeException("User already registered but not verified");
      }
      throw new RuntimeException("Email already in use");
    }

    // 2. Create user
    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .displayName(request.getDisplayName())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .phone(request.getPhone())
        .status(UserStatus.INACTIVE)
        .role(UserRole.ROLE_USER)
        .build();

    user.setCart(new Cart(user)); // Create cart for user

    User savedUser = userRepo.save(user);

    // 3. Create verification token and send email
    VerifyEmailToken verifyEmailToken = VerifyEmailToken.builder()
        .user(savedUser)
        .token(generateVerificationCode())
        .expiryTime(LocalDateTime.now().plusMinutes(1))
        .build();

    verifyEmailTokenRepo.save(verifyEmailToken);

    sendVerificationEmail(savedUser.getEmail(), verifyEmailToken.getToken());

    return UserMapper.INSTANCE.toResponse(savedUser);
  }

  @Override
  public void verify(VerifyRequest request) {
    // 1. Find user by email
    Optional<User> userOpt = userRepo.findByEmailForUpdate(request.getEmail());

    if (userOpt.isEmpty()) {
      throw new ResourceNotFoundException("User", "email", request.getEmail());
    }

    User user = userOpt.get();

    if (user.getStatus() == UserStatus.ACTIVE) {
      throw new RuntimeException("User already verified");
    }

    // 2. Find verification token
    VerifyEmailToken verifyEmailTokenOpt = verifyEmailTokenRepo.findByUserId(user.getId())
        .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

    if (verifyEmailTokenOpt.getExpiryTime().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Token expired");
    }

    if (!verifyEmailTokenOpt.getToken().equals(request.getVerificationCode())) {
      throw new RuntimeException("Invalid verification code");
    }

    // 3. Verify user and delete token
    user.setStatus(UserStatus.ACTIVE);

    userRepo.save(user);

    verifyEmailTokenRepo.delete(verifyEmailTokenOpt);

    log.info("User verified: {}", user.getEmail());
  }

  @Override
  public void resendVerification(String email) {
    // 1. Find user by email
    Optional<User> userOpt = userRepo.findByEmail(email);

    if (userOpt.isEmpty()) {
      throw new ResourceNotFoundException("User", "email", email);
    }

    User user = userOpt.get();

    // 2. Check if user is already verified
    if (user.getStatus() == UserStatus.ACTIVE) {
      throw new RuntimeException("User already verified");
    }

    // 3. Delete existing token and create new one
    verifyEmailTokenRepo.findByUserId(user.getId())
        .ifPresent(verifyEmailTokenRepo::delete);

    VerifyEmailToken verifyEmailToken = VerifyEmailToken.builder()
        .user(user)
        .token(generateVerificationCode())
        .expiryTime(LocalDateTime.now().plusMinutes(1))
        .build();

    verifyEmailTokenRepo.save(verifyEmailToken);

    // 4. Send verification email
    sendVerificationEmail(email, verifyEmailToken.getToken());
  }

  @Override
  public LoginResponse login(LoginRequest request) {
    // 1. Find user by email
    User user = userRepo.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

    // 2. Check if user is verified
    if (user.getStatus().equals(UserStatus.INACTIVE)) {
      throw new RuntimeException("User not verified");
    }

    // 3. Authenticate user and generate token
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));

    String token = "Bearer " + jwtTokenProvider.createToken(user);

    // 4. Return token
    return LoginResponse.builder()
        .token(token)
        .expiresInMs(jwtTokenProvider.getValidityInMilliseconds())
        .build();
  }

  @Override
  public void sendPasswordReset(String email) {
    // 1. Check if password reset email already sent
    Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepo.findByUserEmail(email);

    if (existingTokenOpt.isPresent()) {
      if (existingTokenOpt.get().getExpiryTime().isAfter(LocalDateTime.now())) {
        throw new RuntimeException("Password reset email already sent");
      } else {
        passwordResetTokenRepo.delete(existingTokenOpt.get());
      }
    }

    // 2. Find user by email
    Optional<User> userOpt = userRepo.findByEmail(email);

    if (userOpt.isEmpty()) {
      throw new ResourceNotFoundException("User", "email", email);
    }

    User user = userOpt.get();

    // 3. Create password reset token
    PasswordResetToken passwordResetToken = PasswordResetToken.builder()
        .user(user)
        .expiryTime(LocalDateTime.now().plusHours(1))
        .build();

    passwordResetTokenRepo.save(passwordResetToken);

    // 4. Send password reset email
    String resetUrl = "http://localhost:8080/reset-password?token=" + passwordResetToken.getToken();

    sendPasswordResetEmail(email, resetUrl);
  }

  @Override
  public void verifyPasswordReset(PasswordResetRequest request) {
    // 1. Find and validate password reset token
    Optional<PasswordResetToken> passwordResetTokenOpt = passwordResetTokenRepo.findByToken(request.getToken());

    if (passwordResetTokenOpt.isEmpty()) {
      throw new RuntimeException("Invalid or expired token");
    }

    PasswordResetToken passwordResetToken = passwordResetTokenOpt.get();

    if (passwordResetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Token expired");
    }

    if (!passwordResetToken.getUser().getEmail().equals(request.getEmail())) {
      throw new RuntimeException("Invalid token");
    }

    // 2. Update user password and delete token
    User user = userRepo.findByEmailForUpdate(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepo.save(user);

    passwordResetTokenRepo.delete(passwordResetToken);
  }

  private void sendVerificationEmail(String email, String code) {
    String subject = "Verify your email address";
    String text = "<html>"
        + "<body style=\"font-family: Arial, sans-serif;\">"
        + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
        + "<h2 style=\"color: #333;\">Welcome to our platform!</h2>"
        + "<p style=\"font-size: 16px;\">Please enter the following code to verify your email address:</p>"
        + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">"
        + "<h3 style=\"color: #333; font-size: 24px; margin-top: 0;\">Verification code</h3>"
        + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff; margin-top: 0; margin-bottom: 20px;\">"
        + code + "</p>"
        + "</div>"
        + "<p style=\"font-size: 16px;\">This code will expire in 1 minute.</p>"
        + "</div>"
        + "</body>"
        + "</html>";

    emailService.sendHtmlEmail(email, subject, text);
  }

  private void sendPasswordResetEmail(String email, String resetUrl) {
    String subject = "Reset password request";
    String text = "<html>"
        + "<body style=\"font-family: Arial, sans-serif;\">"
        + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
        + "<h2 style=\"color: #333;\">Password reset request</h2>"
        + "<p style=\"font-size: 16px;\">Please click the link below to reset your password:</p>"
        + "<a href=\"" + resetUrl
        + "\" style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px;\">Reset password</a>"
        + "<p style=\"font-size: 16px;\">This link will expire in 1 hour.</p>"
        + "</div>"
        + "</body>"
        + "</html>";

    emailService.sendHtmlEmail(email, subject, text);
  }

  private String generateVerificationCode() {
    Random random = new Random();

    return String.format("%06d", random.nextInt(999999));
  }
}
