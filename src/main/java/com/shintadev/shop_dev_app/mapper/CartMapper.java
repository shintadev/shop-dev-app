package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.response.cart.CartItemResponse;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartResponse;
import com.shintadev.shop_dev_app.domain.model.cart.Cart;
import com.shintadev.shop_dev_app.domain.model.cart.CartItem;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface CartMapper {

  CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

  CartResponse toResponse(Cart cart);

  @Mapping(target = "product.categoryId", source = "product.category.id")
  CartItemResponse toResponse(CartItem cartItem);

}
