package com.shintadev.shop_dev_app.base;

public interface BaseService<T, ID> {

  public T save(T entity);

  public T findById(ID id);
}
