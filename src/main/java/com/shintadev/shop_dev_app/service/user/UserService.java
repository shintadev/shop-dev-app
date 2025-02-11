package com.shintadev.shop_dev_app.service.user;

import java.util.List;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.domain.dto.request.user.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.user.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.user.UserResponse;

public interface UserService extends BaseService<UserResponse, Long, UserRequest, UserProfileUpdateRequest> {

  UserResponse addAddress(Long userId, AddressRequest request);

  List<AddressResponse> findUserAddresses(Long userId);

  AddressResponse findUserAddress(Long userId, Long addressId);

  UserResponse updateAddress(Long userId, Long addressId, AddressRequest request);

  void deleteAddress(Long userId, Long addressId);

  UserResponse getCurrentUser();

  UserResponse updateCurrentUser(UserProfileUpdateRequest request);

  // Page<OrderResponse> findUserOrderHistory(Long userId, Pageable pageable);
}
