package com.shintadev.shop_dev_app.domain.dto.request.cart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemRequest {

  private Long productId;

  private Integer quantity;
}
