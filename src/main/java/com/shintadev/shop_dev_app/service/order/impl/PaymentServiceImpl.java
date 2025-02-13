package com.shintadev.shop_dev_app.service.order.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.enums.order.PaymentStatus;
import com.shintadev.shop_dev_app.domain.model.order.Order;
import com.shintadev.shop_dev_app.repository.order.OrderRepo;
import com.shintadev.shop_dev_app.service.order.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final PayOS payos;
  private final OrderRepo orderRepo;

  @Override
  public CheckoutResponseData createPayment(Order order) {
    try {
      // 1. Create item data
      List<ItemData> itemData = order.getOrderItems().stream()
          .map(item -> ItemData.builder()
              .name(item.getProduct().getName())
              .price(item.getProduct().getPrice().intValue())
              .quantity(item.getQuantity())
              .build())
          .toList();

      // 2. Create payment data
      PaymentData paymentData = PaymentData.builder()
          .orderCode(Long.parseLong(order.getOrderNumber()))
          .amount(order.getTotalPrice().intValue())
          .description("")
          .items(itemData)
          .returnUrl(null)
          .cancelUrl(null)
          .build();

      // 3. Create checkout response data
      return payos.createPaymentLink(paymentData);
    } catch (Exception e) {
      log.error("Error creating payment", e.getMessage());
      throw new RuntimeException("Error creating payment", e);
    }
  }

  @Override
  public void processPayment(String orderNumber, Webhook webhook) {
    try {
      // 1. Get order from database
      Order order = orderRepo.findByOrderNumber(orderNumber);

      // 2. Check if order is paid
      if (order.getPaymentStatus() == PaymentStatus.PAID) {
        throw new RuntimeException("Order already paid");
      }

      // 3. Verify webhook data
      WebhookData webhookData = payos.verifyPaymentWebhookData(webhook);
      log.info("Webhook data: {}", webhookData);

      if (Boolean.FALSE.equals(webhook.getSuccess())) {
        log.info("Payment failed for order {}", orderNumber);
        log.info("Error code: {}", webhook.getCode());
        log.info("Error description: {}", webhook.getDesc());
        order.setPaymentStatus(PaymentStatus.CANCELLED);
      } else {
        log.info("Payment successful for order {}", orderNumber);
        log.info("Result code: {}", webhook.getCode());
        log.info("Result description: {}", webhook.getDesc());
        order.setPaymentStatus(PaymentStatus.PAID);
      }

      // 4. Save order
      orderRepo.save(order);

      // 5. Send Kafka event
      // TODO: Send Kafka event
    } catch (Exception e) {
      log.error("Error processing payment", e.getMessage());
      throw new RuntimeException("Error processing payment", e);
    }
  }
}
