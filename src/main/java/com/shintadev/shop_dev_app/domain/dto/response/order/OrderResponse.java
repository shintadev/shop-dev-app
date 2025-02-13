package com.shintadev.shop_dev_app.domain.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shintadev.shop_dev_app.domain.dto.response.user.AddressResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {

  private Long id;
  private String orderNumber;
  private String status;
  private BigDecimal totalPrice;
  private AddressResponse address;
  private List<OrderItemResponse> orderItems;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  // private PaymentResponse payment;
}
