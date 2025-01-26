package com.shintadev.shop_dev_app.service.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.service.RedisService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisServiceImpl implements RedisService {

  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Override
  public void setString(String key, String value) {
    if(!StringUtils.hasLength(key)){
      log.error("Key is empty or null");
      return;
    }

    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public String getString(String key) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(key))
      .map(String::valueOf)
      .orElse(null);
  }

  @Override
  public void setObject(String key, Object value) {
    if(!StringUtils.hasLength(key)){
      log.error("Key is empty or null");
      return;
    }

    try {
      redisTemplate.opsForValue().set(key, value);
    } catch (Exception e) {
      log.error("Error setting object to Redis: " + e.getMessage());
    }
  }

  @Override
  public <T> T getObject(String key, Class<T> clazz) {
    Object result = redisTemplate.opsForValue().get(key);
    
    if(result == null){
      return null;
    }

    if(result instanceof Map){
      try {
        return objectMapper.convertValue(result, clazz);
      } catch (Exception e) {
        log.error("Error converting value to object: " + e.getMessage());
        return null;
      }
    }

    if(result instanceof String){
      try {
        return objectMapper.readValue((String) result, clazz);
      } catch (Exception e) {
        log.error("Error converting value to object: " + e.getMessage());
        return null;
      }
    }

    return null;
  }

  @Override
  public void delete(String key) {
    redisTemplate.delete(key);
  }

  @Override
  public RedisTemplate<String, Object> getRedisTemplate() {
    return redisTemplate;
  }
}
