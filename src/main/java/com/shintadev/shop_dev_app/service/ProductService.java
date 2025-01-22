package com.shintadev.shop_dev_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.payload.product.ProductDto;

public interface ProductService extends BaseService<ProductDto, Long, ProductDto, ProductDto> {

  public Page<ProductDto> findByName(String name, Pageable pageable);

}
