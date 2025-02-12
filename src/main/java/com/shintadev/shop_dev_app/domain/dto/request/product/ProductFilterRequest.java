package com.shintadev.shop_dev_app.domain.dto.request.product;

import java.math.BigDecimal;

import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFilterRequest {

  private String categoryId;

  @PositiveOrZero(message = "Min price must not be negative")
  private BigDecimal minPrice;

  @PositiveOrZero(message = "Max price must not be negative")
  private BigDecimal maxPrice;

  @Size(max = 4096, message = "Keyword must not exceed 4096 characters")
  private String keyword;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;
}
