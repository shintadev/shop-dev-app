package com.shintadev.shop_dev_app.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.dto.product.ProductDto;
import com.shintadev.shop_dev_app.model.product.Product;
import com.shintadev.shop_dev_app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public List<Product> findByName(String name) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByName'");
  }

  @Override
  public Product create(ProductDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Product findOne(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findOne'");
  }

  @Override
  public Product update(Long id, ProductDto t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public boolean isExists(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isExists'");
  }

}
