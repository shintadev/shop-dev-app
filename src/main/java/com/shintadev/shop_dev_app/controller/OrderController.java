package com.shintadev.shop_dev_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.service.OrderService;

@RestController
public class OrderController {

  @Autowired
  private OrderService orderService;

}
