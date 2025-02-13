package com.shintadev.shop_dev_app.service.order.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.order.OrderRequest;
import com.shintadev.shop_dev_app.domain.dto.response.order.OrderResponse;
import com.shintadev.shop_dev_app.domain.enums.order.OrderSource;
import com.shintadev.shop_dev_app.domain.enums.order.OrderStatus;
import com.shintadev.shop_dev_app.domain.model.cart.Cart;
import com.shintadev.shop_dev_app.domain.model.cart.CartItem;
import com.shintadev.shop_dev_app.domain.model.order.Order;
import com.shintadev.shop_dev_app.domain.model.order.OrderItem;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.OrderMapper;
import com.shintadev.shop_dev_app.repository.order.OrderRepo;
import com.shintadev.shop_dev_app.repository.product.ProductRepo;
import com.shintadev.shop_dev_app.repository.user.AddressRepo;
import com.shintadev.shop_dev_app.service.cart.CartService;
import com.shintadev.shop_dev_app.service.order.OrderService;
import com.shintadev.shop_dev_app.service.order.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

  private final OrderRepo orderRepo;
  private final CartService cartService;
  private final AddressRepo addressRepo;
  private final PaymentService paymentService;
  private final ProductRepo productRepo;
  private final OrderMapper orderMapper;

  @Override
  public OrderResponse placeOrder(OrderRequest request) {
    // 1. Get current user
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // 2. Get address from database
    Address address = addressRepo.findById(request.getAddress().getId())
        .orElseGet(() -> addressRepo.save(request.getAddress()));

    try {
      // 3. Create order items based on order source
      Set<OrderItem> orderItems = switch (request.getOrderSource()) {
        case BUY_NOW -> createBuyNowOrderItems(request);
        case CART_SELECTED -> createSelectedCartOrderItems(currentUser, request.getSelectedCartItemIds());
      };

      // 4. Check if order items are empty
      if (orderItems.isEmpty()) {
        throw new IllegalArgumentException("No items to order");
      }

      // 5. Calculate total price
      double totalPrice = calculateTotalPrice(orderItems);

      // 6. Create order
      Order order = Order.builder()
          .user(currentUser)
          .status(OrderStatus.PENDING)
          .address(address)
          .orderNumber(UUID.randomUUID().toString())
          .totalPrice(totalPrice)
          .orderItems(orderItems)
          .build();

      // 7. Save order
      order = orderRepo.save(order);

      // 8. Create payment
      paymentService.createPayment(order, request.getPaymentMethod());

      // 9. Clear cart's item
      if (request.getOrderSource() == OrderSource.CART_SELECTED) {
        clearCartItems(currentUser, request);
      }

      // 10. Send Kafka event
      // TODO: Send Kafka event

      // 11. Return order response
      return orderMapper.toResponse(order);
    } catch (Exception e) {
      log.error("Order placement failed", e);
      throw new RuntimeException("Failed to place order: " + e.getMessage());
    }
  }

  private Set<OrderItem> createBuyNowOrderItems(OrderRequest request) {
    // 1. Get product from database
    Product product = productRepo.findById(request.getProductId())
        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId().toString()));

    // 2. Create order item
    OrderItem orderItem = OrderItem.builder()
        .product(product)
        .quantity(request.getQuantity())
        .unitPrice(product.getPrice())
        .subtotal(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
        .build();

    // 3. Return order item as a set
    return Set.of(orderItem);
  }

  private Set<OrderItem> createSelectedCartOrderItems(User user, List<Long> selectedCartItemIds) {
    // 1. Get cart from database
    Cart cart = cartService.getCartByUserId(user.getId());

    // 2. Filter selected cart items
    List<CartItem> selectedItems = cart.getCartItems().stream()
        .filter(item -> selectedCartItemIds.contains(item.getId()))
        .toList();

    // 3. Convert selected cart items to order items
    return convertCartItemsToOrderItems(selectedItems);
  }

  private Set<OrderItem> convertCartItemsToOrderItems(Collection<CartItem> cartItems) {
    // 1. Convert cart items to order items
    return cartItems.stream()
        .map(cartItem -> OrderItem.builder()
            .product(cartItem.getProduct())
            .quantity(cartItem.getQuantity())
            .unitPrice(cartItem.getProduct().getPrice())
            .subtotal(cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
            .build())
        .collect(Collectors.toSet());
  }

  private double calculateTotalPrice(Set<OrderItem> orderItems) {
    // 1. Calculate total price
    return orderItems.stream()
        .mapToDouble(item -> item.getSubtotal().doubleValue())
        .sum();
  }

  private void clearCartItems(User user, OrderRequest request) {
    // 1. Check if order source is cart-based
    if (request.getOrderSource() == OrderSource.CART_SELECTED) {
      // 2. Remove selected cart items
      cartService.removeCartItems(user.getId(), request.getSelectedCartItemIds());
    }
  }

  @Override
  public Page<OrderResponse> getUserOrders(Pageable pageable) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return orderRepo.findByUserId(currentUser.getId(), pageable).map(orderMapper::toResponse);
  }

  @Override
  public Page<OrderResponse> findAll(Pageable pageable) {
    return orderRepo.findAll(pageable).map(orderMapper::toResponse);
  }
}
