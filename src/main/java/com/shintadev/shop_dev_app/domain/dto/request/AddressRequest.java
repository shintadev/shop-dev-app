package com.shintadev.shop_dev_app.domain.dto.request;

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
public class AddressRequest {

  @NotBlank(message = "Street is required")
  @Size(max = 255, message = "Street must not exceed 255 characters")
  private String street;

  @NotBlank(message = "City is required")
  @Size(max = 128, message = "City must not exceed 128 characters")
  private String city;

  @NotBlank(message = "Province is required")
  @Size(max = 128, message = "Province must not exceed 128 characters")
  private String province;

  @NotBlank(message = "Postal code is required")
  @Size(max = 16, message = "Postal code must not exceed 16 characters")
  private String postalCode;

  @Size(max = 16, message = "Phone number must not exceed 16 characters")
  private String phone;

  private boolean isDefault;
}
