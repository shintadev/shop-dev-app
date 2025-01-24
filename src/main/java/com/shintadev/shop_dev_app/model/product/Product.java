package com.shintadev.shop_dev_app.model.product;

import java.util.List;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.model.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "products")
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

  @Column(name = "name", length = 128, nullable = false)
  private String name;

  @Column(name = "price", nullable = false)
  private double price;

  @Column(name = "stock", nullable = false)
  private int stock;

  @Column(name = "description", length = Integer.MAX_VALUE, nullable = true)
  private String description;

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
