package com.shintadev.shop_dev_app.domain.model.user;

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
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(name = "street", length = 255, nullable = false)
  private String street;

  @NotNull
  @Column(name = "city", length = 128, nullable = false)
  private String city;

  @NotNull
  @Column(name = "province", length = 128, nullable = false)
  private String province;

  @NotNull
  @Column(name = "postal_code", length = 16, nullable = false)
  private String postalCode;

  @Column(name = "is_default", nullable = false, columnDefinition = "boolean default false")
  private boolean isDefault;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
