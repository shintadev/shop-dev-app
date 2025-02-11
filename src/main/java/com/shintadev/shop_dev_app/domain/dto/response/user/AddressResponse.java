package com.shintadev.shop_dev_app.domain.dto.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

  private Long id;
  private String street;
  private String city;
  private String province;
  private String postalCode;
  private String phone;
  private boolean isDefault;
}
