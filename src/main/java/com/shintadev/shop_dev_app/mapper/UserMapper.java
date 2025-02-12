package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.request.user.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.user.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.user.UserResponse;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "avatarUrl", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "cart", ignore = true)
  User toEntity(UserRequest request);

  UserResponse toResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "cart", ignore = true)
  void updateFromRequest(UserProfileUpdateRequest request, @MappingTarget User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "isDefault", ignore = true)
  @Mapping(target = "user", ignore = true)
  Address toEntity(AddressRequest request);

  @Mapping(target = "isDefault", source = "default")
  AddressResponse toResponse(Address address);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  void updateFromRequest(AddressRequest request, @MappingTarget Address address);
}
