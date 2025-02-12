package com.shintadev.shop_dev_app.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<Response, ID, CreateRequest, UpdateRequest> {

  Response create(CreateRequest request);

  Page<Response> findAll(Pageable pageable);

  Response findOne(ID id);

  Response update(ID id, UpdateRequest request);

  void delete(ID id);

  boolean isExists(ID id);
}
