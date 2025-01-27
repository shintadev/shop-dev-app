package com.shintadev.shop_dev_app.domain.model.product;

import java.math.BigDecimal;
import java.util.List;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.model.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "products")
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

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private List<ProductMedia> productMedias;

  @ManyToMany(fetch = FetchType.LAZY)
  @ToString.Exclude
  @JoinTable(name = "product_orders", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "order_id"))
  private List<Order> orders;
}
