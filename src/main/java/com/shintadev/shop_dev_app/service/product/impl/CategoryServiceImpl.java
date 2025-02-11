package com.shintadev.shop_dev_app.service.product.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.product.CategoryRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.CategoryResponse;
import com.shintadev.shop_dev_app.domain.model.product.Category;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.ProductMapper;
import com.shintadev.shop_dev_app.repository.product.CategoryRepo;
import com.shintadev.shop_dev_app.service.product.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepo categoryRepo;

  private final ProductMapper productMapper;

  /* CREATE */

  @Override
  public CategoryResponse create(CategoryRequest request) {
    // 1. Check if the category name already exists
    if (categoryRepo.existsByName(request.getName())) {
      log.error("Category name {} already exists", request.getName());
      throw new ResourceNotFoundException("Category", "name", request.getName());
    }

    // 2. Create a new category
    Category category = Category.builder()
        .name(request.getName())
        .build();

    // 3. Set the parent category for the category
    if (request.getParentId() != null) {
      Category parentCategory = categoryRepo.findById(Long.parseLong(request.getParentId()))
          .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getParentId()));
      category.setParent(parentCategory);
    }

    // 4. Save the category
    Category savedCategory = categoryRepo.save(category);

    return productMapper.toResponse(savedCategory);
  }

  /* READ */

  @Override
  public Page<CategoryResponse> findAll(Pageable pageable) {
    Page<Category> categories = categoryRepo.findAll(pageable);

    return categories.map(productMapper::toResponse);
  }

  @Override
  public List<CategoryResponse> findRootCategories() {
    List<Category> categories = categoryRepo.findByParentIsNull();

    return categories.stream()
        .map(productMapper::toResponse)
        .toList();
  }

  @Override
  public CategoryResponse findOne(Long id) {
    Category category = categoryRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));

    return productMapper.toResponse(category);
  }

  /* UPDATE */

  @Override
  public CategoryResponse update(Long id, CategoryRequest request) {
    // 1. Check if the category exists
    Category category = categoryRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));

    productMapper.updateFromRequest(request, category);

    // 2. Set the parent category for the category
    if (request.getParentId() != null) {
      Category parentCategory = categoryRepo.findById(Long.parseLong(request.getParentId()))
          .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getParentId()));
      category.setParent(parentCategory);
    } else {
      category.setParent(null);
    }

    // 3. Save the category
    Category updatedCategory = categoryRepo.save(category);

    return productMapper.toResponse(updatedCategory);
  }

  /* DELETE */

  @Override
  public void delete(Long id) {
    Category category = categoryRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id.toString()));

    categoryRepo.delete(category);
  }

  @Override
  public boolean isExists(Long id) {
    return categoryRepo.existsById(id);
  }

}
