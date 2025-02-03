package com.shintadev.shop_dev_app.domain.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterRequest {

  private String category;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
}
