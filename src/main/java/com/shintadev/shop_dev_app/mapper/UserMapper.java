package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.shintadev.shop_dev_app.domain.dto.request.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "slug", ignore = true)
  @Mapping(target = "avatarUrl", ignore = true)
  @Mapping(target = "firebaseUid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toEntity(UserRequest request);

  UserResponse toResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "firebaseUid", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateFromRequest(UserProfileUpdateRequest request, @MappingTarget User user);

  Address toEntity(AddressRequest request);

  AddressResponse toResponse(Address address);

  void updateFromRequest(AddressRequest request, @MappingTarget Address address);
}
