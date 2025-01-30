package com.shintadev.shop_dev_app.domain.model.order;

import java.math.BigDecimal;

import com.shintadev.shop_dev_app.domain.model.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
@Builder
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Positive
  @Column(name = "quantity", nullable = false)
  private int quantity;

  @NotNull
  @Column(name = "unit_price", nullable = false)
  private BigDecimal unitPrice;

  @NotNull
  @Column(name = "subtotal", nullable = false)
  private BigDecimal subtotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
