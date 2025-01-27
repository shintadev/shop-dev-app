package com.shintadev.shop_dev_app.domain.model.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_media")
public class ProductMedia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "media_url", length = 1024, nullable = false)
  private String mediaUrl;

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;
}
