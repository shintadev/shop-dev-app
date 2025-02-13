package com.shintadev.shop_dev_app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.service.order.PaymentService;

import lombok.RequiredArgsConstructor;
import vn.payos.type.Webhook;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/{orderNumber}")
  public void processPayment(@PathVariable String orderNumber, @RequestBody Webhook webhook) {
    paymentService.processPayment(orderNumber, webhook);
  }
}
