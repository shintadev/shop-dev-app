package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.request.product.CategoryRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.request.product.ProductUpdateRequest;
import com.shintadev.shop_dev_app.domain.dto.response.product.CategoryResponse;
import com.shintadev.shop_dev_app.domain.dto.response.product.ProductResponse;
import com.shintadev.shop_dev_app.domain.model.product.Category;
import com.shintadev.shop_dev_app.domain.model.product.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  @Mapping(target = "slug", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Product toEntity(ProductRequest request);

  @Mapping(target = "categoryId", source = "category.id")
  ProductResponse toResponse(Product product);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  @Mapping(target = "slug", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateFromRequest(ProductUpdateRequest request, @MappingTarget Product product);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "parent", ignore = true)
  @Mapping(target = "subcategories", ignore = true)
  @Mapping(target = "products", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Category toEntity(CategoryRequest request);

  CategoryResponse toResponse(Category category);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "parent", ignore = true)
  @Mapping(target = "subcategories", ignore = true)
  @Mapping(target = "products", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateFromRequest(CategoryRequest request, @MappingTarget Category category);
}
