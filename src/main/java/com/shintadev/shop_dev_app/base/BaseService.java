package com.shintadev.shop_dev_app.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID, RequestDto> {

  T create(RequestDto request);

  Page<T> findAll(Pageable pageable);

  T findOne(ID id);

  T update(ID id, RequestDto request);

  void delete(ID id);

  boolean isExists(ID id);
}
