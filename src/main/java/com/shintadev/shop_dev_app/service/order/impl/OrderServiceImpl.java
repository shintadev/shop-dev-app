package com.shintadev.shop_dev_app.service.order.impl;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.service.order.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  // private final OrderRepo orderRepo;

  // private ObjectMapper objectMapper;

  // OrderServiceImpl(OrderRepo orderRepo, ObjectMapper objectMapper) {
  // this.orderRepo = orderRepo;
  // this.objectMapper = objectMapper;
  // }

  // @Override
  // @Transactional
  // public OrderDto create(OrderDto orderDto) {
  // Order order = convertToEntity(orderDto);
  // Order newOrder = orderRepo.saveAndFlush(order);

  // return convertToDto(newOrder);
  // }

  // @Override
  // public Page<OrderDto> findAll(Pageable pageable) {
  // Page<Order> orders = orderRepo.findAll(pageable);

  // return orders.map(this::convertToDto);
  // }

  // @Override
  // public OrderDto findOne(Long id) {
  // Order order = orderRepo.findById(id).orElse(null);

  // return convertToDto(order);
  // }

  // @Override
  // @Transactional
  // public OrderDto update(Long id, OrderDto orderDto) {
  // if (!isExists(id)) {
  // return null;
  // }

  // Order order = convertToEntity(orderDto);
  // order.setId(id);
  // Order updatedOrder = orderRepo.saveAndFlush(order);

  // return convertToDto(updatedOrder);
  // }

  // @Override
  // @Transactional
  // public void delete(Long id) {
  // Order order = orderRepo.findById(id).orElse(null);
  // if (order == null) {
  // return;
  // }

  // order.setDeleted(true);
  // orderRepo.saveAndFlush(order);
  // }

  // @Override
  // public boolean isExists(Long id) {
  // return orderRepo.existsById(id);
  // }

  // @Override
  // public Page<OrderDto> findByUserId(Long userId, Pageable pageable) {
  // Page<Order> orders = orderRepo.findByUserId(userId, pageable);

  // return orders.map(this::convertToDto);
  // }

  // private OrderDto convertToDto(Order order) {
  // return objectMapper.convertValue(order, OrderDto.class);
  // }

  // private Order convertToEntity(OrderDto orderDto) {
  // return objectMapper.convertValue(orderDto, Order.class);
  // }
}
