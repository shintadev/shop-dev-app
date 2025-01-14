package com.shintadev.shop_dev_app.base;

import java.util.List;

public interface BaseService<T, ID, CreateDto, UpdateDto> {

  T create(CreateDto t);

  List<T> findAll();

  T findOne(ID id);

  T update(ID id, UpdateDto t);

  void delete(ID id);
}
