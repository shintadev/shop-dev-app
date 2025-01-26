package com.shintadev.shop_dev_app.service;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisService {

  void setString(String key, String value);

  String getString(String key);

  void setObject(String key, Object value);

  <T> T getObject(String key, Class<T> clazz);

  void delete(String key);

  RedisTemplate<String, Object> getRedisTemplate();
}
