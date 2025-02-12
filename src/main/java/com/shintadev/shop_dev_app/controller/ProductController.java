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

import com.shintadev.shop_dev_app.domain.dto.request.product.ProductFilterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.ProductResponse;
import com.shintadev.shop_dev_app.service.product.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

  private final ProductService productService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<ProductResponse> addProduct(@Valid @RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<Page<ProductResponse>> getAllProducts(
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
        : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id,
      @RequestBody ProductUpdateRequest request) {
    return new ResponseEntity<>(productService.update(Long.parseLong(id), request), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable String id) {
    productService.delete(Long.parseLong(id));

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @GetMapping("/published")
  public ResponseEntity<Page<ProductResponse>> getPublishedProducts(
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
        : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(productService.getPublishedProducts(pageable), HttpStatus.OK);
  }

  @GetMapping("/{slug}")
  public ResponseEntity<ProductResponse> getProductDetail(@PathVariable String slug) {
    return new ResponseEntity<>(productService.findBySlug(slug), HttpStatus.OK);
  }

  @GetMapping("/{slug}/related")
  public ResponseEntity<List<ProductResponse>> getBySlug(@PathVariable String slug) {
    return new ResponseEntity<>(productService.getRelatedProducts(slug), HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<ProductResponse>> searchProduct(@RequestParam("q") String keyword,
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
        : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(productService.search(keyword, pageable), HttpStatus.OK);
  }

  @GetMapping("/filter")
  public ResponseEntity<Page<ProductResponse>> filterProduct(@RequestBody ProductFilterRequest request,
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
        : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(productService.filter(request, pageable), HttpStatus.OK);
  }
}
