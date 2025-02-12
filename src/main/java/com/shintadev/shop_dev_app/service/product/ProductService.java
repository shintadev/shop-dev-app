package com.shintadev.shop_dev_app.service.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductFilterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.ProductResponse;

public interface ProductService extends BaseService<ProductResponse, Long, ProductRequest, ProductUpdateRequest> {

  Page<ProductResponse> getPublishedProducts(Pageable pageable);

  Page<ProductResponse> filter(ProductFilterRequest request, Pageable pageable);

  Page<ProductResponse> adminFilter(ProductFilterRequest request, Pageable pageable);

  Page<ProductResponse> search(String keyword, Pageable pageable);

  ProductResponse findBySlug(String slug);

  List<ProductResponse> getRelatedProducts(String slug);

}
