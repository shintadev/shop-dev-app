package com.shintadev.shop_dev_app.domain.dto.response.cart;

import com.shintadev.shop_dev_app.domain.dto.response.product.ProductResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {

  private ProductResponse product;

  private Integer quantity;
}
