package com.shintadev.shop_dev_app.service;

import java.util.List;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.dto.product.ProductDto;
import com.shintadev.shop_dev_app.model.product.Product;

public interface ProductService extends BaseService<Product, Long, ProductDto, ProductDto> {
  public List<Product> findByName(String name);

}
