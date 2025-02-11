package com.shintadev.shop_dev_app.domain.dto.response.product;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponse {

  private Long id;
  private String name;
  private CategoryResponse parent;
  private Set<CategoryResponse> subcategories;
}
