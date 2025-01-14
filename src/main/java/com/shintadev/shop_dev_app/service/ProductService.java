package com.shintadev.shop_dev_app.service;

import java.util.List;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.model.Product;

public interface ProductService extends BaseService<Product, Long> {
  public List<Product> findByName(String name);

}
