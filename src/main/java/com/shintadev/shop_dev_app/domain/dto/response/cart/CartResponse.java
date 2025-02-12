package com.shintadev.shop_dev_app.domain.dto.response.cart;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {

  private List<CartItemResponse> cartItems;
}
