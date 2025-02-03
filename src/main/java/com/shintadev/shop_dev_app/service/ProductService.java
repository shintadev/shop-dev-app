package com.shintadev.shop_dev_app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.domain.dto.request.ProductFilterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.response.ProductResponse;

public interface ProductService extends BaseService<ProductResponse, Long, ProductRequest> {

  Page<ProductResponse> findByFilter(ProductFilterRequest request, Pageable pageable);

  Page<ProductResponse> findByName(String name, Pageable pageable);

  ProductResponse findBySlug(String slug);

  boolean updateStock(Long id, int quantity);
}
