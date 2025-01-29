package com.shintadev.shop_dev_app.domain.model.order;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.kafka.common.protocol.types.Field.Str;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.enums.OrderStatus;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.domain.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

  @NotNull
  @Column(name = "order_number", length = 128, nullable = false, unique = true)
  private String orderNumber;

  @NotNull
  @Column(name = "total_price", nullable = false)
  private double totalPrice;
  
  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "varchar(16) default 'PENDING'")
  private OrderStatus status;

  @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false, nullable = false)
  private User user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  private Set<OrderItem> orderItems = new HashSet<>();

}
