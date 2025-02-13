package com.shintadev.shop_dev_app.service.product.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shintadev.shop_dev_app.domain.dto.request.product.ProductFilterRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.ProductResponse;
import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;
import com.shintadev.shop_dev_app.domain.model.product.Category;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.exception.BusinessException;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.ProductMapper;
import com.shintadev.shop_dev_app.repository.product.CategoryRepo;
import com.shintadev.shop_dev_app.repository.product.ProductRepo;
import com.shintadev.shop_dev_app.service.product.ProductService;
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

  private final CategoryRepo categoryRepo;

  /* CREATE */

  @Override
  public ProductResponse create(ProductRequest request) {
    // 1. Check if the product name already exists
    if (productRepo.existsByName(request.getName())) {
      log.error("Product name {} already exists", request.getName());
      throw new BusinessException("Product name already exists", "PRODUCT_NAME_ALREADY_EXISTS");
    }

    // 2. Create a new product
    Product product = productMapper.toEntity(request);
    String slug = StringUtil.generateSlug(product.getName());
    product.setSlug(slug);
    product.setStatus(ProductStatus.ACTIVE);

    // 3. Set the category for the product
    if (request.getCategoryId() != null) {
      Category category = categoryRepo.findById(Long.parseLong(request.getCategoryId()))
          .orElseThrow(
              () -> new ResourceNotFoundException(Category.class.getSimpleName(), "id", request.getCategoryId()));
      product.setCategory(category);
    }

    // 4. Upload images to S3
    if (request.getImages() != null && !request.getImages().isEmpty()) {
      // List<String> imageUrls = s3Service.uploadImages(request.getImages());
      // product.setImages(imageUrls);
    }

    // 5. Save the product
    Product savedProduct = productRepo.save(product);

    // TODO: Publish event to Kafka

    return productMapper.toResponse(savedProduct);
  }

  /* READ */

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> findAll(Pageable pageable) {
    Page<Product> products = productRepo.findAll(pageable);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> adminFilter(ProductFilterRequest request, Pageable pageable) {
    Page<Product> products = productRepo.findProductsByAdminFilters(
        request.getCategoryId(),
        request.getMinPrice(),
        request.getMaxPrice(),
        request.getKeyword(),
        request.getStatus().name(),
        pageable);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getPublishedProducts(Pageable pageable) {
    Page<Product> products = productRepo.findPublicProducts(pageable);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse findOne(Long id) {
    Product product = productRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(Product.class.getSimpleName(), "id", id.toString()));

    return productMapper.toResponse(product);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse findBySlug(String slug) {
    Product product = productRepo.findBySlug(slug)
        .orElseThrow(() -> new ResourceNotFoundException(Product.class.getSimpleName(), "slug", slug));

    return productMapper.toResponse(product);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductResponse> getRelatedProducts(String slug) {
    List<Product> relatedProducts = productRepo.findRelatedProducts(slug,
        PageRequest.of(0, 10));

    return relatedProducts.stream()
        .map(productMapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> filter(ProductFilterRequest request, Pageable pageable) {
    Page<Product> products = productRepo.findProductsByFilters(
        request.getCategoryId(),
        request.getMinPrice(),
        request.getMaxPrice(),
        request.getKeyword(),
        pageable);

    return products.map(productMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> search(String keyword, Pageable pageable) {
    Page<Product> products = productRepo.findByKeyword(keyword, pageable);

    return products.map(productMapper::toResponse);
  }

  /* UPDATE */

  @Override
  public ProductResponse update(Long id, ProductUpdateRequest request) {
    // 1. Check if the product exists
    Product existingProduct = productRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException(Product.class.getSimpleName(), "id", id.toString()));

    productMapper.updateFromRequest(request, existingProduct);

    // 2. Upload images to S3 and update the image URLs
    if (request.getImages() != null && !request.getImages().isEmpty()) {
      // List<String> imageUrls = s3Service.uploadImages(request.getImages());
      // existingProduct.getImageUrls().addAll(imageUrls);
    }
    if (request.getDeletedImageUrls() != null && !request.getDeletedImageUrls().isEmpty()) {
      // s3Service.deleteImages(request.getDeletedImageUrls());
      // existingProduct.getImageUrls().removeAll(request.getDeletedImageUrls());
    }

    // 3. Save the product
    Product updatedProduct = productRepo.save(existingProduct);

    // TODO: Publish event to Kafka

    return productMapper.toResponse(updatedProduct);
  }

  /* DELETE */

  @Override
  public void delete(Long id) {
    Product product = productRepo.findByIdForUpdate(id)
        .orElseThrow(() -> new ResourceNotFoundException(Product.class.getSimpleName(), "id", id.toString()));

    product.setStatus(ProductStatus.DELETED);

    productRepo.save(product); // TODO: Soft delete
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExists(Long id) {
    return productRepo.existsById(id);
  }
}
