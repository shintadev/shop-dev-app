package com.shintadev.shop_dev_app.domain.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

  private Long id;
  private String street;
  private String city;
  private String province;
  private String postalCode;
  private String phone;
  private boolean isDefault;
}
