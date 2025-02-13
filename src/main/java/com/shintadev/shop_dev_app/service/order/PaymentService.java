package com.shintadev.shop_dev_app.service.order;

import com.shintadev.shop_dev_app.domain.model.order.Order;

import vn.payos.type.CheckoutResponseData;
import vn.payos.type.Webhook;

public interface PaymentService {
  CheckoutResponseData createPayment(Order order);

  void processPayment(String orderNumber, Webhook webhook);
}
