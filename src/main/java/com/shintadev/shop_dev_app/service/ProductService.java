package com.shintadev.shop_dev_app.service;

import java.util.List;

import com.shintadev.shop_dev_app.model.product.Product;

public interface ProductService {
  public List<Product> findByName(String name);

}
