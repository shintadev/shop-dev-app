package com.shintadev.shop_dev_app.service.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.domain.dto.request.order.OrderRequest;
import com.shintadev.shop_dev_app.domain.dto.response.order.OrderResponse;

public interface OrderService {

  Page<OrderResponse> findAll(Pageable pageable);

  Page<OrderResponse> getUserOrders(Pageable pageable);

  OrderResponse placeOrder(OrderRequest request);
}
