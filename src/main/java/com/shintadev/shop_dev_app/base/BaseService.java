package com.shintadev.shop_dev_app.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID, CreateDto, UpdateDto> {

  T create(CreateDto t);

  Page<T> findAll(Pageable pageable);

  T findOne(ID id);

  T update(ID id, UpdateDto t);

  void delete(ID id);

  boolean isExists(ID id);
}
