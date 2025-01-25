package com.shintadev.shop_dev_app.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shintadev.shop_dev_app.model.product.Product;
import com.shintadev.shop_dev_app.payload.product.ProductDto;
import com.shintadev.shop_dev_app.repository.ProductRepo;
import com.shintadev.shop_dev_app.service.ProductService;
import com.shintadev.shop_dev_app.util.StringUtil;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepo productRepo;

  private ObjectMapper objectMapper;

  ProductServiceImpl(ProductRepo productRepo, ObjectMapper objectMapper) {
    this.productRepo = productRepo;
    this.objectMapper = objectMapper;
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
    Page<Product> products = productRepo.findAll(pageable);

    return products.map(this::convertToDto);
  }

  @Override
  public ProductDto findOne(Long id) {
    Product product = productRepo.findById(id).orElse(null);

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
    Page<Product> products = productRepo.findByNameLike(name, pageable);

    return products.map(this::convertToDto);
  }

  @Override
  public ProductDto findBySlug(String slug) {
    Product product = productRepo.findBySlug(slug);

    return convertToDto(product);
  }

  private Product convertToEntity(ProductDto productDto) {
    return objectMapper.convertValue(productDto, Product.class);
  }

  private ProductDto convertToDto(Product product) {
    return objectMapper.convertValue(product, ProductDto.class);
  }
}
