package com.shintadev.shop_dev_app.domain.dto.response.product;

import java.math.BigDecimal;
import java.util.List;

import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

  private String name;
  private String description;
  private BigDecimal price;
  private int stock;
  private String slug;
  private String categoryId;
  private ProductStatus status;
  private List<String> imageUrls;
}
