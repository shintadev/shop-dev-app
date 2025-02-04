package com.shintadev.shop_dev_app.domain.dto.request;

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

  @Size(max = 100, message = "First name must not exceed 100 characters")
  private String firstName;

  @Size(max = 100, message = "Last name must not exceed 100 characters")
  private String lastName;

  @Size(max = 16, message = "Phone number must not exceed 16 characters")
  private String phone;
}
