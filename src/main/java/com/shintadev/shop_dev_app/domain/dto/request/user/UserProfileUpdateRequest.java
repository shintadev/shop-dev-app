package com.shintadev.shop_dev_app.domain.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequest {

  @Size(max = 64, message = "First name must not exceed 64 characters")
  private String firstName;

  @Size(max = 64, message = "Last name must not exceed 64 characters")
  private String lastName;

  @Size(max = 128, message = "Display name must not exceed 128 characters")
  private String displayName;

  @Size(max = 16, message = "Phone number must not exceed 16 characters")
  private String phone;

  @Size(max = 256, message = "Avatar URL must not exceed 256 characters")
  private String avatarUrl;
}
