package com.shintadev.shop_dev_app.service.product;

import java.util.List;

import com.shintadev.shop_dev_app.base.BaseService;
import com.shintadev.shop_dev_app.domain.dto.request.product.CategoryRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.CategoryResponse;

public interface CategoryService extends BaseService<CategoryResponse, Long, CategoryRequest, CategoryRequest> {

  List<CategoryResponse> findRootCategories();
}
