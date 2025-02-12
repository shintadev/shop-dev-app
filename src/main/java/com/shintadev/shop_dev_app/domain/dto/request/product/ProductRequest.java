package com.shintadev.shop_dev_app.domain.dto.request.product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {

  @NotBlank(message = "Name is required")
  @Size(max = 128, message = "Name must not exceed 128 characters")
  private String name;

  @Size(max = 4096, message = "Description must not exceed 4096 characters")
  private String description;

  @NotNull(message = "Price is required")
  @Positive(message = "Price must be greater than 0")
  private BigDecimal price;

  @NotNull(message = "Stock quantity is required")
  @PositiveOrZero(message = "Stock quantity must not be negative")
  private int stock;

  @NotBlank(message = "Category is required")
  private String categoryId;

  @Size(max = 5, message = "Maximum 5 images are allowed")
  private List<MultipartFile> images;
}
