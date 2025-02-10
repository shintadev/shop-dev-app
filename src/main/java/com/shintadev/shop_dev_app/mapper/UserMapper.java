package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.request.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "avatarUrl", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "verificationCode", ignore = true)
  @Mapping(target = "verificationCodeExpirationAt", ignore = true)
  User toEntity(UserRequest request);

  UserResponse toResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  @Mapping(target = "orders", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "verificationCode", ignore = true)
  @Mapping(target = "verificationCodeExpirationAt", ignore = true)
  @Mapping(target = "avatarUrl", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  void updateFromRequest(UserProfileUpdateRequest request, @MappingTarget User user);

  @Mapping(target = "id", ignore = true)
  Address toEntity(AddressRequest request);

  @Mapping(target = "id", ignore = true)
  AddressResponse toResponse(Address address);

  @Mapping(target = "id", ignore = true)
  void updateFromRequest(AddressRequest request, @MappingTarget Address address);
}
