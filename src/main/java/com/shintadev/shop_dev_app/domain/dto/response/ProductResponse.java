package com.shintadev.shop_dev_app.domain.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private String slug;
  private String category;
  private ProductStatus status;
  private List<String> imageUrls;
}
