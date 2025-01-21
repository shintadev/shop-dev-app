package com.shintadev.shop_dev_app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.model.product.Product;
import com.shintadev.shop_dev_app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public List<Product> findByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

}
