package com.shintadev.shop_dev_app.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.payload.product.ProductDto;
import com.shintadev.shop_dev_app.repository.ProductRepo;
import com.shintadev.shop_dev_app.service.ProductService;
import com.shintadev.shop_dev_app.service.RedisService;
import com.shintadev.shop_dev_app.util.StringUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepo productRepo;

  private final ObjectMapper objectMapper;
  
  private final RedisService redisService;

  ProductServiceImpl(ProductRepo productRepo, 
            ObjectMapper objectMapper,
            RedisService redisService) {
    this.productRepo = productRepo;
    this.objectMapper = objectMapper;
    this.redisService = redisService;
  }

  @Override
  @Transactional
  public ProductDto create(ProductDto t) {
    Product product = convertToEntity(t);
    String slug = StringUtil.generateSlug(product);
    product.setSlug(slug);
    Product newProduct = productRepo.saveAndFlush(product);

    return convertToDto(newProduct);
  }

  @Override
  public Page<ProductDto> findAll(Pageable pageable) {
    // Check if the products are in Redis
    String key = "products_" + pageable.getPageNumber();
    Page<ProductDto> productsFromRedis = redisService.getObject(key, Page.class);
    if (productsFromRedis != null) {
      log.info("Products found in Redis");
      return productsFromRedis;
    }

    // If not found in Redis, get from database
    Page<Product> products = productRepo.findAll(pageable);

    // Save to Redis
    redisService.setObject(key, products);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return products.map(this::convertToDto);
  }

  @Override
  public ProductDto findOne(Long id) {
    // Check if the product is in Redis
    String key = "product_" + id;
    ProductDto productFromRedis = redisService.getObject(key, ProductDto.class);
    if (productFromRedis != null) {
      log.info("Product found in Redis");
      return productFromRedis;
    }

    // If not found in Redis, get from database
    Product product = productRepo.findById(id).orElse(null);

    // Save to Redis
    redisService.setObject(key, product);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);

    return convertToDto(product);
  }

  @Override
  @Transactional
  public ProductDto update(Long id, ProductDto t) {
    if (!isExists(id)) {
      return null;
    }

    Product product = convertToEntity(t);
    product.setId(id);
    Product updatedProduct = productRepo.saveAndFlush(product);

    return convertToDto(updatedProduct);
  }

  @Override
  @Transactional
  public ProductDto delete(Long id) {
    Product product = productRepo.findById(id).orElse(null);
    if (product == null) {
      return null;
    }

    product.setDeleted(true);
    Product deletedProduct = productRepo.saveAndFlush(product);

    return convertToDto(deletedProduct);
  }

  @Override
  public boolean isExists(Long id) {
    return productRepo.existsById(id);
  }

  @Override
  public Page<ProductDto> findByName(String name, Pageable pageable) {
    // Check if the products are in Redis
    String key = "products_" + name + "_" + pageable.getPageNumber();
    Page<ProductDto> productsFromRedis = redisService.getObject(key, Page.class);
    if (productsFromRedis != null) {
      log.info("Products found in Redis");
      return productsFromRedis;
    }

    // If not found in Redis, get from database
    Page<Product> products = productRepo.findByNameLike(name, pageable);

    // Save to Redis
    redisService.setObject(key, products);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);
    
    return products.map(this::convertToDto);
  }

  @Override
  public ProductDto findBySlug(String slug) {
    // Check if the product is in Redis
    String key = "product_" + slug;
    ProductDto productFromRedis = redisService.getObject(key, ProductDto.class);
    if (productFromRedis != null) {
      log.info("Product found in Redis");
      return productFromRedis;
    }

    // If not found in Redis, get from database
    Product product = productRepo.findBySlug(slug);

    // Save to Redis
    redisService.setObject(key, product);
    redisService.getRedisTemplate().expire(key, 1, TimeUnit.HOURS);
    
    return convertToDto(product);
  }

  private Product convertToEntity(ProductDto productDto) {
    return objectMapper.convertValue(productDto, Product.class);
  }

  private ProductDto convertToDto(Product product) {
    return objectMapper.convertValue(product, ProductDto.class);
  }
}
