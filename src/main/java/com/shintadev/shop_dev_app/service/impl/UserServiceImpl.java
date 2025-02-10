package com.shintadev.shop_dev_app.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.AddressRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserProfileUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.request.UserRequest;
import com.shintadev.shop_dev_app.domain.dto.response.AddressResponse;
import com.shintadev.shop_dev_app.domain.dto.response.UserResponse;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.user.Address;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.mapper.UserMapper;
import com.shintadev.shop_dev_app.repository.user.UserRepo;
import com.shintadev.shop_dev_app.service.RedisService;
import com.shintadev.shop_dev_app.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;

  private final UserMapper userMapper;

  private final RedisService redisService;

  /* CREATE */
  @Override
  public UserResponse create(UserRequest request) {
    if (userRepo.existsByEmail(request.getEmail())) {
      log.error("Email {} already exists", request.getEmail());
      throw new IllegalArgumentException("Email already in use");
    }

    User user = userMapper.toEntity(request);
    user.setStatus(UserStatus.ACTIVE);

    User savedUser = userRepo.saveAndFlush(user);

    // TODO: Publish event to Kafka

    return userMapper.toResponse(savedUser);
  }

  @Override
  public UserResponse addAddress(Long id, AddressRequest request) {
    User user = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));

    Address address = userMapper.toEntity(request);

    // Ensure only one default address
    if (Boolean.TRUE.equals(request.isDefault())) {
      user.getAddresses().forEach(a -> a.setDefault(false));
    }

    user.getAddresses().add(address);
    address.setUser(user);

    User updatedUser = userRepo.saveAndFlush(user);

    return userMapper.toResponse(updatedUser);
  }

  /* READ */

  @Override
  @Transactional(readOnly = true)
  public Page<UserResponse> findAll(Pageable pageable) {
    // Check if the users are in Redis
    String key = "users_" + pageable.getPageNumber();
    Page<UserResponse> usersFromRedis = redisService.getObject(key, Page.class);
    if (usersFromRedis != null) {
      log.info("Users found in Redis");
      return usersFromRedis;
    }

    // If not found in Redis, get from database
    Page<User> users = userRepo.findAll(pageable);

    // Save to Redis
    redisService.setObject(key, users);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return users.map(userMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse findOne(Long id) {
    // Check if the user is in Redis
    String key = "user_" + id;
    UserResponse userFromRedis = redisService.getObject(key, UserResponse.class);
    if (userFromRedis != null) {
      log.info("User found in Redis");
      return userFromRedis;
    }

    // If not found in Redis, get from database
    User user = userRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));

    // Save to Redis
    redisService.setObject(key, user);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse findByEmail(String email) {
    // Check if the user is in Redis
    String key = "user_" + email;
    UserResponse userFromRedis = redisService.getObject(key, UserResponse.class);
    if (userFromRedis != null) {
      log.info("User found in Redis");
      return userFromRedis;
    }

    // If not found in Redis, get from database
    User user = userRepo.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found with email " + email));

    // Save to Redis
    redisService.setObject(key, user);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return userMapper.toResponse(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AddressResponse> findUserAddresses(Long userId) {
    User user = userRepo.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    return user.getAddresses().stream()
        .map(userMapper::toResponse)
        .collect(Collectors.toList());
  }

  /* UPDATE */

  @Override
  public UserResponse update(Long id, UserProfileUpdateRequest userDto) {
    User existingUser = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));

    userMapper.updateFromRequest(userDto, existingUser);

    // TODO: Update firebase

    User updatedUser = userRepo.saveAndFlush(existingUser);

    // TODO: Send message to Kafka

    return userMapper.toResponse(updatedUser);
  }

  @Override
  public UserResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
    User user = userRepo.findByIdForUpdate(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Address address = user.getAddresses().stream()
        .filter(a -> a.getId().equals(addressId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));

    userMapper.updateFromRequest(request, address);

    // Ensure only one default address
    if (Boolean.TRUE.equals(request.isDefault())) {
      user.getAddresses().forEach(a -> {
        if (!a.getId().equals(addressId)) {
          a.setDefault(false);
        }
      });
    }

    User updatedUser = userRepo.saveAndFlush(user);

    return userMapper.toResponse(updatedUser);
  }

  /* DELETE */

  @Override
  public void delete(Long id) {
    User user = userRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new RuntimeException("User not found with id " + id));

    user.setStatus(UserStatus.INACTIVE);
    user.setDeleted(true);
    userRepo.saveAndFlush(user);

    // TODO: Send message to Kafka
  }

  @Override
  public void deleteAddress(Long userId, Long addressId) {
    User user = userRepo.findByIdForUpdate(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

    Address address = user.getAddresses().stream()
        .filter(a -> a.getId().equals(addressId))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Address not found with id " + addressId));

    user.getAddresses().remove(address);
    userRepo.saveAndFlush(user);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExists(Long id) {
    return userRepo.existsById(id);
  }
}
