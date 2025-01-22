package com.shintadev.shop_dev_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.model.Order;
import com.shintadev.shop_dev_app.payload.order.OrderDto;
import com.shintadev.shop_dev_app.repository.OrderRepo;
import com.shintadev.shop_dev_app.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepo orderRepo;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  @Transactional
  public OrderDto create(OrderDto orderDto) {
    Order order = convertToEntity(orderDto);
    Order newOrder = orderRepo.saveAndFlush(order);

    return convertToDto(newOrder);
  }

  @Override
  public Page<OrderDto> findAll(Pageable pageable) {
    Page<Order> orders = orderRepo.findAll(pageable);

    return orders.map(this::convertToDto);
  }

  @Override
  public OrderDto findOne(Long id) {
    Order order = orderRepo.findById(id).orElse(null);

    return convertToDto(order);
  }

  @Override
  @Transactional
  public OrderDto update(Long id, OrderDto orderDto) {
    if (!isExists(id)) {
      return null;
    }

    Order order = convertToEntity(orderDto);
    order.setId(id);
    Order updatedOrder = orderRepo.saveAndFlush(order);

    return convertToDto(updatedOrder);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!isExists(id)) {
      log.error("Order with id {} not found", id);
      return;
    }

    orderRepo.deleteById(id);
  }

  @Override
  public boolean isExists(Long id) {
    return orderRepo.existsById(id);
  }

  @Override
  public Page<OrderDto> findByUserId(Long userId, Pageable pageable) {
    Page<Order> orders = orderRepo.findByUserId(userId, pageable);

    return orders.map(this::convertToDto);
  }

  private OrderDto convertToDto(Order order) {
    return objectMapper.convertValue(order, OrderDto.class);
  }

  private Order convertToEntity(OrderDto orderDto) {
    return objectMapper.convertValue(orderDto, Order.class);
  }
}
