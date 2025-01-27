package com.shintadev.shop_dev_app.domain.model;

import java.util.List;
import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.domain.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
@Builder
public class Order extends BaseEntity {

  @Column(name = "total_price", nullable = false)
  private double totalPrice;

  @ManyToMany(mappedBy = "orders")
  @ToString.Exclude
  private List<Product> products;

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
  private User user;
}
