package com.shintadev.shop_dev_app.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.response.ProductResponse;
import com.shintadev.shop_dev_app.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<ProductResponse> getById(@PathVariable String id) {
    return new ResponseEntity<>(productService.findOne(Long.parseLong(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponse>> getAll(
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

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
    return new ResponseEntity<>(productService.update(Long.parseLong(id), request), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable String id) {
    productService.delete(Long.parseLong(id));

    return new ResponseEntity<>(id, HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Page<ProductResponse>> getByName(
      @PathVariable String name,
      @RequestParam int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "desc") String order) {
    int pageNumber = Math.max(0, page - 1);
    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC
        : Sort.Direction.DESC;
    Sort sortBy = Sort.by(direction, sort);
    Pageable pageable = PageRequest.of(pageNumber, size, sortBy);
    return new ResponseEntity<>(productService.findByName(name, pageable), HttpStatus.OK);
  }

  @GetMapping("/{slug}")
  public ResponseEntity<ProductResponse> getBySlug(@PathVariable String slug) {
    return new ResponseEntity<>(productService.findBySlug(slug), HttpStatus.OK);
  }

}
