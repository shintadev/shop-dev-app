package com.shintadev.shop_dev_app.service.user.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.user.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.user.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.user.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.user.UserResponse;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.UserMapper;
import com.shintadev.shop_dev_app.repository.user.UserRepo;
import com.shintadev.shop_dev_app.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  /* CREATE */
  @Override
  public UserResponse create(UserRequest request) {
    // 1. Check if the email already exists
    if (userRepo.existsByEmail(request.getEmail())) {
      log.error("Email {} already exists", request.getEmail());
      throw new RuntimeException("Email already in use");
    }

    // 2. Create a new user
    User user = userMapper.toEntity(request);
    user.setPassword(passwordEncoder
        .encode(request
            .getEmail()
            .subSequence(0, request
                .getEmail()
                .indexOf('@'))));

    User savedUser = userRepo.save(user);

    // TODO: Publish event to Kafka

    return userMapper.toResponse(savedUser);
  }

  @Override
  public UserResponse addAddress(Long id, AddressRequest request) {
    // 1. Find the user
    User user = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

    // 2. Create a new address
    Address address = userMapper.toEntity(request);
    address.setUser(user);

    // 3. Ensure only one default address
    if (Boolean.TRUE.equals(request.isDefault())) {
      user.getAddresses()
          .forEach(a -> a.setDefault(false));
    }

    // 4. Add the address to the user and save
    user.getAddresses().add(address);

    User updatedUser = userRepo.save(user);

    return userMapper.toResponse(updatedUser);
  }

  /* READ */

  @Override
  @Transactional(readOnly = true)
  public UserResponse getCurrentUser() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserResponse> findAll(Pageable pageable) {
    Page<User> users = userRepo.findAll(pageable);

    return users.map(userMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse findOne(Long id) {
    User user = userRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AddressResponse> findUserAddresses(Long userId) {
    User user = userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

    return user.getAddresses().stream()
        .map(userMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public AddressResponse findUserAddress(Long userId, Long addressId) {
    User user = userRepo.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

    Address address = user.getAddresses().stream()
        .filter(a -> a.getId().equals(addressId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId.toString()));

    return userMapper.toResponse(address);
  }

  /* UPDATE */

  @Override
  public UserResponse updateCurrentUser(UserProfileUpdateRequest request) {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    userMapper.updateFromRequest(request, user);

    User updatedUser = userRepo.save(user);

    // TODO: Send message to Kafka

    return userMapper.toResponse(updatedUser);
  }

  @Override
  public UserResponse update(Long id, UserProfileUpdateRequest request) {
    User existingUser = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

    userMapper.updateFromRequest(request, existingUser);

    User updatedUser = userRepo.save(existingUser);

    // TODO: Send message to Kafka

    return userMapper.toResponse(updatedUser);
  }

  @Override
  public UserResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
    // 1. Find the user and address
    User user = userRepo.findByIdForUpdate(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

    Address address = user.getAddresses().stream()
        .filter(a -> a.getId().equals(addressId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId.toString()));

    userMapper.updateFromRequest(request, address);

    // 2. Ensure only one default address
    if (Boolean.TRUE.equals(request.isDefault())) {
      user.getAddresses().forEach(a -> {
        if (!a.getId().equals(addressId)) {
          a.setDefault(false);
        }
      });
    }

    // 3. Save the user
    User updatedUser = userRepo.save(user);

    return userMapper.toResponse(updatedUser);
  }

  /* DELETE */

  @Override
  public void delete(Long id) {
    User user = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

    user.setStatus(UserStatus.DELETED);
    user.setDeleted(true);

    userRepo.saveAndFlush(user);

    // TODO: Send message to Kafka
  }

  @Override
  public void deleteAddress(Long userId, Long addressId) {
    // 1. Find the user and address
    User user = userRepo.findByIdForUpdate(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

    Address address = user.getAddresses().stream()
        .filter(a -> a.getId().equals(addressId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId.toString()));

    // 2. Remove the address from the user
    user.getAddresses().remove(address);

    // 3. Set new default address if the deleted address was the default
    if (address.isDefault() && !user.getAddresses().isEmpty()) {
      user.getAddresses().stream()
          .findFirst()
          .ifPresent(a -> a.setDefault(true));
    }

    // 4. Save the user
    userRepo.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExists(Long id) {
    return userRepo.existsById(id);
  }
}
