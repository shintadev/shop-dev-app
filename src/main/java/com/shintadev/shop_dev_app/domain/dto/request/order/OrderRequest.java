package com.shintadev.shop_dev_app.domain.dto.request.order;

import com.shintadev.shop_dev_app.domain.enums.order.OrderSource;
import com.shintadev.shop_dev_app.domain.enums.order.PaymentMethod;
import com.shintadev.shop_dev_app.domain.model.user.Address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequest {
  // For all sources
  @NotNull(message = "Address is required")
  private Address address;

  @NotNull(message = "Payment method is required")
  private PaymentMethod paymentMethod;

  @NotNull(message = "Order source is required")
  private OrderSource orderSource;

  // For BUY_NOW
  private Long productId;

  @Positive(message = "Quantity must be positive")
  private Integer quantity;

  // For CART_SELECTED
  private List<Long> selectedCartItemIds;
}
