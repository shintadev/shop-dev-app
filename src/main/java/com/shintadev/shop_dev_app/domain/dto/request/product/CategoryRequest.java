package com.shintadev.shop_dev_app.domain.dto.request.product;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {

  @Size(max = 128, message = "Name must not exceed 128 characters")
  private String name;

  private String parentId;
}
