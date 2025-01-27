package com.shintadev.shop_dev_app.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.domain.model.User;
import com.shintadev.shop_dev_app.payload.user.UserDto;
import com.shintadev.shop_dev_app.repository.UserRepo;
import com.shintadev.shop_dev_app.service.RedisService;
import com.shintadev.shop_dev_app.service.UserService;
import com.shintadev.shop_dev_app.util.StringUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;

  private final ObjectMapper objectMapper;

  private final RedisService redisService;

  UserServiceImpl(
    UserRepo userRepo, 
    ObjectMapper objectMapper,
    RedisService redisService) {
    this.userRepo = userRepo;
    this.objectMapper = objectMapper;
    this.redisService = redisService;
  }

  @Override
  @Transactional
  public UserDto create(UserDto userDto) {
    User user = convertToEntity(userDto);
    String slug = StringUtil.generateSlug(user);
    user.setSlug(slug);
    User newUser = userRepo.saveAndFlush(user);

    return convertToDto(newUser);
  }

  @Override
  public Page<UserDto> findAll(Pageable pageable) {
    // Check if the users are in Redis
    String key = "users_" + pageable.getPageNumber();
    Page<UserDto> usersFromRedis = redisService.getObject(key, Page.class);
    if (usersFromRedis != null) {
      log.info("Users found in Redis");
      return usersFromRedis;
    }

    // If not found in Redis, get from database
    Page<User> users = userRepo.findAll(pageable);

    // Save to Redis
    redisService.setObject(key, users);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return users.map(this::convertToDto);
  }

  @Override
  public UserDto findOne(Long id) {
    // Check if the user is in Redis
    String key = "user_" + id;
    UserDto userFromRedis = redisService.getObject(key, UserDto.class);
    if (userFromRedis != null) {
      log.info("User found in Redis");
      return userFromRedis;
    }

    // If not found in Redis, get from database
    User user = userRepo.findById(id).orElse(null);

    // Save to Redis
    redisService.setObject(key, user);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return convertToDto(user);
  }

  @Override
  @Transactional
  public UserDto update(Long id, UserDto userDto) {
    if (!isExists(id)) {
      return null;
    }

    User user = convertToEntity(userDto);
    user.setId(id);
    User updatedUser = userRepo.saveAndFlush(user);

    return convertToDto(updatedUser);
  }

  @Override
  @Transactional
  public UserDto delete(Long id) {
    User user = userRepo.findById(id).orElse(null);
    if (user == null) {
      log.error("User with id {} not found", id);
      return null;
    }

    user.setDeleted(true);
    User deletedUser = userRepo.saveAndFlush(user);

    return convertToDto(deletedUser);
  }

  @Override
  public boolean isExists(Long id) {
    return userRepo.existsById(id);
  }

  @Override
  public UserDto findByEmail(String email) {
    // Check if the user is in Redis
    String key = "user_" + email;
    UserDto userFromRedis = redisService.getObject(key, UserDto.class);
    if (userFromRedis != null) {
      log.info("User found in Redis");
      return userFromRedis;
    }

    // If not found in Redis, get from database
    User user = userRepo.findByEmail(email);

    // Save to Redis
    redisService.setObject(key, user);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return convertToDto(user);
  }

  @Override
  public UserDto findBySlug(String slug) {
    // Check if the user is in Redis
    String key = "user_" + slug;
    UserDto userFromRedis = redisService.getObject(key, UserDto.class);
    if (userFromRedis != null) {
      log.info("User found in Redis");
      return userFromRedis;
    }

    // If not found in Redis, get from database
    User user = userRepo.findBySlug(slug);

    // Save to Redis
    redisService.setObject(key, user);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);
    
    return convertToDto(user);
  }

  private UserDto convertToDto(User user) {
    return objectMapper.convertValue(user, UserDto.class);
  }

  private User convertToEntity(UserDto userDto) {
    return objectMapper.convertValue(userDto, User.class);
  }
}
