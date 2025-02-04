package com.shintadev.shop_dev_app.service.impl;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shintadev.shop_dev_app.domain.dto.request.ProductFilterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.response.ProductResponse;
import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.mapper.ProductMapper;
import com.shintadev.shop_dev_app.repository.product.ProductRepo;
import com.shintadev.shop_dev_app.service.ProductService;
import com.shintadev.shop_dev_app.service.RedisService;
import com.shintadev.shop_dev_app.util.StringUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepo productRepo;

  private final ProductMapper productMapper;
  
  private final RedisService redisService;

  /* CREATE */

  @Override
  public ProductResponse create(ProductRequest request) {
    validateProductRequest(request);

    Product product = productMapper.toEntity(request);
    String slug = StringUtil.generateSlug(product);
    product.setSlug(slug);
    product.setStatus(ProductStatus.ACTIVE);

    if (request.getImages() != null && !request.getImages().isEmpty()) {
      // List<String> imageUrls = s3Service.uploadImages(request.getImages());
      // product.setImages(imageUrls);
    }

    Product savedProduct = productRepo.saveAndFlush(product);

    // TODO: Publish event to Kafka

    return productMapper.toResponse(savedProduct);
  }

  /* READ */

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> findAll(Pageable pageable) {
    // Check if the products are in Redis
    String key = "products_" + pageable.getPageNumber();
    Page<ProductResponse> productsFromRedis = redisService.getObject(key, Page.class);
    if (productsFromRedis != null) {
      log.info("Products found in Redis");
      return productsFromRedis;
    }

    // If not found in Redis, get from database
    Page<Product> products = productRepo.findAll(pageable);

    // Save to Redis
    redisService.setObject(key, products);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse findOne(Long id) {
    // Check if the product is in Redis
    String key = "product_" + id;
    ProductResponse productFromRedis = redisService.getObject(key, ProductResponse.class);
    if (productFromRedis != null) {
      log.info("Product found in Redis");
      return productFromRedis;
    }

    // If not found in Redis, get from database
    Product product = productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    // Save to Redis
    ProductResponse productDto = productMapper.toResponse(product);
    redisService.setObject(key, productDto);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return productDto;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> findByName(String name, Pageable pageable) {
    // Check if the products are in Redis
    String key = "products_" + name + "_" + pageable.getPageNumber();
    Page<ProductResponse> productsFromRedis = redisService.getObject(key, Page.class);
    if (productsFromRedis != null) {
      log.info("Products found in Redis");
      return productsFromRedis;
    }

    // If not found in Redis, get from database
    Page<Product> products = productRepo.findByNameLike(name, pageable);

    // Save to Redis
    redisService.setObject(key, products);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse findBySlug(String slug) {
    // Check if the product is in Redis
    String key = "product_" + slug;
    ProductResponse productFromRedis = redisService.getObject(key, ProductResponse.class);
    if (productFromRedis != null) {
      log.info("Product found in Redis");
      return productFromRedis;
    }

    // If not found in Redis, get from database
    Product product = productRepo.findBySlug(slug)
        .orElseThrow(() -> new RuntimeException("Product not found with slug: " + slug));

    // Save to Redis
    redisService.setObject(key, product);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return productMapper.toResponse(product);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> findByFilter(ProductFilterRequest request, Pageable pageable) {

    Page<Product> products = productRepo.findProductsByFilters(
        request.getCategory(), request.getMinPrice(), request.getMaxPrice(), pageable);

    return products.map(productMapper::toResponse);
  }

  /* UPDATE */

  @Override
  public ProductResponse update(Long id, ProductRequest request) {
    Product existingProduct = productRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    validateProductRequest(request);
    productMapper.updateFromRequest(request, existingProduct);

    if (request.getImages() != null && !request.getImages().isEmpty()) {
      // s3Service.deleteImages(existingProduct.getImageUrls());
      // List<String> imageUrls = s3Service.uploadImages(request.getImages());
      // existingProduct.setImages(imageUrls);
    }

    Product updatedProduct = productRepo.saveAndFlush(existingProduct);

    // TODO: Publish event to Kafka

    return productMapper.toResponse(updatedProduct);
  }

  @Override
  public boolean updateStock(Long id, int quantity) {
    int updatedRows = productRepo.updateStock(id, quantity);

    return updatedRows > 0;
  }

  /* DELETE */

  @Override
  public void delete(Long id) {
    Product product = productRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    product.setStatus(ProductStatus.INACTIVE);
    product.setDeleted(true);
    productRepo.saveAndFlush(product);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExists(Long id) {
    return productRepo.existsById(id);
  }

  private void validateProductRequest(ProductRequest request) {
    if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Price must be greater than 0");
    }

    if (request.getStock() < 0) {
      throw new IllegalArgumentException("Stock must be greater than or equal to 0");
    }
  }
}
