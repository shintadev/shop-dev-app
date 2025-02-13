package com.shintadev.shop_dev_app.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.response.order.OrderItemResponse;
import com.shintadev.shop_dev_app.domain.dto.response.order.OrderResponse;
import com.shintadev.shop_dev_app.domain.model.cart.CartItem;
import com.shintadev.shop_dev_app.domain.model.order.Order;
import com.shintadev.shop_dev_app.domain.model.order.OrderItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  @Mapping(target = "address.isDefault", source = "address.default")
  @Mapping(target = "checkoutResponseData", ignore = true)
  OrderResponse toResponse(Order order);

  @Mapping(target = "productName", source = "product.name")
  OrderItemResponse toResponse(OrderItem orderItem);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "unitPrice", source = "cartItem.product.price")
  @Mapping(target = "subtotal", expression = "java(calculateSubtotal(cartItem))")
  @Mapping(target = "order", ignore = true)
  OrderItem convertToOrderItem(CartItem cartItem);

  default BigDecimal calculateSubtotal(CartItem cartItem) {
    return cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
  }
}
