package com.shintadev.shop_dev_app.domain.dto.response.order;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {

  private String productName;
  private BigDecimal unitPrice;
  private int quantity;
  private BigDecimal subtotal;
}
