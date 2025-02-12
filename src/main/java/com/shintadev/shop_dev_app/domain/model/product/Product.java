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
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name = "products",
    indexes = {
        @Index(name = "idx_product_name", columnList = "name"),
        @Index(name = "idx_product_slug", columnList = "slug"),
        @Index(name = "idx_product_status", columnList = "status")
    })
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
public class Product extends BaseEntity {

  @NotNull
  @Column(name = "name", length = 128, nullable = false)
  private String name;

  @Column(name = "description", length = 4096)
  private String description;

  @NotNull
  @Positive
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @NotNull
  @PositiveOrZero
  @Column(name = "stock", nullable = false)
  @Builder.Default
  private int stock = 1;

  @NotNull
  @Column(name = "slug", length = 128, nullable = false, unique = true)
  private String slug;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  @ToString.Exclude
  private Category category;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  @Builder.Default
  private ProductStatus status = ProductStatus.ACTIVE;

  @ElementCollection
  @CollectionTable(
      name = "product_images",
      joinColumns = @JoinColumn(name = "product_id")
  )
  private List<String> imageUrls;
}
