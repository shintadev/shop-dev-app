package com.shintadev.shop_dev_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.shintadev.shop_dev_app.domain.dto.request.ProductRequest;
import com.shintadev.shop_dev_app.domain.dto.response.ProductResponse;
import com.shintadev.shop_dev_app.domain.model.product.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  @Mapping(target = "slug", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Product toEntity(ProductRequest request);

  ProductResponse toResponse(Product product);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  @Mapping(target = "imageUrls", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateFromRequest(ProductRequest request, @MappingTarget Product product);
}
