package com.shintadev.shop_dev_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.payload.order.OrderDto;

public interface OrderService extends BaseService<OrderDto, Long, OrderDto> {

  Page<OrderDto> findByUserId(Long userId, Pageable pageable);

}
