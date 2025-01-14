package com.shintadev.shop_dev_app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.model.Product;
import com.shintadev.shop_dev_app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public Product save(Product entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  @Override
  public Product findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public List<Product> findByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

}
