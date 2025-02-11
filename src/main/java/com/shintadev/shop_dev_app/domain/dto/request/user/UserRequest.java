package com.shintadev.shop_dev_app.domain.dto.request.user;

import com.shintadev.shop_dev_app.domain.enums.user.UserRole;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

  @NotBlank(message = "First name is required")
  @Size(max = 64, message = "First name must not exceed 64 characters")
  private String firstName;

  @NotBlank(message = "Last name is required")
  @Size(max = 64, message = "Last name must not exceed 64 characters")
  private String lastName;

  @Size(max = 128, message = "Display name must not exceed 128 characters")
  private String displayName;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = 128, message = "Email must not exceed 128 characters")
  private String email;

  @Size(max = 36, message = "Password must not exceed 36 characters")
  private String password;

  @Size(max = 16, message = "Phone number must not exceed 16 characters")
  private String phone;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private UserStatus status = UserStatus.ACTIVE;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private UserRole role = UserRole.ROLE_USER;
}
