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
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "addresses")
@Builder
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "street", length = 255, nullable = false)
  private String street;

  @NotNull
  @Column(name = "city", length = 128, nullable = false)
  private String city;

  @NotNull
  @Column(name = "province", length = 128, nullable = false)
  private String province;

  @Column(name = "postal_code", length = 16)
  private String postalCode;

  @NotNull
  @Column(name = "phone", length = 16, nullable = false)
  private String phone;

  @Column(name = "is_default", nullable = false)
  @Builder.Default
  private boolean isDefault = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @ToString.Exclude
  private User user;
}
