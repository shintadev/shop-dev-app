package com.shintadev.shop_dev_app.service.order;

import com.shintadev.shop_dev_app.domain.enums.order.PaymentMethod;
import com.shintadev.shop_dev_app.domain.model.order.Order;
import com.shintadev.shop_dev_app.domain.model.order.Payment;

public interface PaymentService {
  Payment createPayment(Order order, PaymentMethod paymentMethod);

  Payment processPayment(Payment payment);
}
