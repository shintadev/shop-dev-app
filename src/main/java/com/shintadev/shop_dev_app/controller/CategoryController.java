package com.shintadev.shop_dev_app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.product.CategoryRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.CategoryResponse;
import com.shintadev.shop_dev_app.service.product.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

  private final CategoryService categoryService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest request) {
    return new ResponseEntity<>(categoryService.create(request), HttpStatus.CREATED);
  }

  @GetMapping("/all")
  public ResponseEntity<Page<CategoryResponse>> getAllCategories(
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> getRootCategories() {
    return new ResponseEntity<>(categoryService.findRootCategories(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
    return new ResponseEntity<>(categoryService.findOne(id), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
      @Valid @RequestBody CategoryRequest request) {
    return new ResponseEntity<>(categoryService.update(id, request), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
