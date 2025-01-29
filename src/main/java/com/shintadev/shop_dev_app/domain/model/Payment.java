package com.shintadev.shop_dev_app.domain.model;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.enums.PaymentMethod;
import com.shintadev.shop_dev_app.domain.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "payments")
@EqualsAndHashCode(callSuper = false)
public class Payment extends BaseEntity{

  @NotNull
  @Column(name = "payment_number", nullable = false, unique = true)
  private String paymentNumber;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "varchar(16) default 'PENDING'")
  private PaymentStatus status;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "payment_method", nullable = false)
  private PaymentMethod paymentMethod;

  @NotNull
  @Column(name = "amount", nullable = false)
  private double amount;

  
}
