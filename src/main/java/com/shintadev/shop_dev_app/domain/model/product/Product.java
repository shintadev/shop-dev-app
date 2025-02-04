package com.shintadev.shop_dev_app.domain.model.product;

import java.math.BigDecimal;
import java.util.List;
import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.enums.product.ProductStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "products",
    indexes = {
				@Index(name = "idx_product_slug", columnList = "slug"),
        @Index(name = "idx_product_category_id", columnList = "category_id"),
        @Index(name = "idx_product_status", columnList = "status")
    })
@EqualsAndHashCode(callSuper = false)
@Builder
public class Product extends BaseEntity {

  @NotNull
  @Column(name = "name", length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = Integer.MAX_VALUE, nullable = true)
  private String description;

  @NotNull
  @Positive
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @NotNull
  @Positive
  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "slug", length = 128, nullable = false, unique = true)
  private String slug;

  @Column(name = "category", length = 128, nullable = false)
  private String category;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "varchar(16) default 'ACTIVE'")
  private ProductStatus status;

  @ElementCollection
  @CollectionTable(
      name = "product_images",
      joinColumns = @JoinColumn(name = "product_id")
  )
  private List<String> imageUrls;
}
