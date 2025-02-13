package com.shintadev.shop_dev_app.service.order.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.enums.order.PaymentMethod;
import com.shintadev.shop_dev_app.domain.enums.order.PaymentStatus;
import com.shintadev.shop_dev_app.domain.model.order.Order;
import com.shintadev.shop_dev_app.domain.model.order.Payment;
import com.shintadev.shop_dev_app.repository.order.PaymentRepo;
import com.shintadev.shop_dev_app.service.order.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepo paymentRepo;

  @Override
  public Payment createPayment(Order order, PaymentMethod paymentMethod) {
    Payment payment = Payment.builder()
        .order(order)
        .paymentNumber(UUID.randomUUID().toString())
        .status(PaymentStatus.PENDING)
        .paymentMethod(paymentMethod)
        .amount(order.getTotalPrice())
        .build();

    return paymentRepo.save(payment);
  }

  @Override
  public Payment processPayment(Payment payment) {
    // TODO: Implement payment processing
    throw new UnsupportedOperationException("Payment processing not implemented");
  }
}
