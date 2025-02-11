package com.shintadev.shop_dev_app.domain.dto.response.user;

import java.time.LocalDateTime;
import java.util.List;

import com.shintadev.shop_dev_app.domain.enums.user.UserRole;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String displayName;
  private String email;
  private String phone;
  private UserStatus status;
  private UserRole role;
  private String avatarUrl;
  private List<AddressResponse> addresses;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
