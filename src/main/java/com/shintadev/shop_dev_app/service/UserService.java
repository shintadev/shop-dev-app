package com.shintadev.shop_dev_app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.domain.dto.request.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;

public interface UserService extends BaseService<UserResponse, Long, UserRequest, UserProfileUpdateRequest> {

  UserResponse getCurrentUser();

  UserResponse findByEmail(String email);

  UserResponse addAddress(Long userId, AddressRequest request);

  UserResponse updateAddress(Long userId, Long addressId, AddressRequest request);

  void deleteAddress(Long userId, Long addressId);

  List<AddressResponse> findUserAddresses(Long userId);

  // Page<OrderResponse> findUserOrderHistory(Long userId, Pageable pageable);
}
