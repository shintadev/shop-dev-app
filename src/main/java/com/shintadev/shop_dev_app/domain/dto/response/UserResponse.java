package com.shintadev.shop_dev_app.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private UserStatus status;
  private String avatarUrl;
  private List<AddressResponse> addresses;
  private LocalDateTime createdAt;
}
