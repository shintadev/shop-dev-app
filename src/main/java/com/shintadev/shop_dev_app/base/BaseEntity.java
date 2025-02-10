package com.shintadev.shop_dev_app.base;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shintadev.shop_dev_app.common.SoftDeletable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
public abstract class BaseEntity implements SoftDeletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(nullable = false, columnDefinition = "boolean default false")
  private boolean deleted;

  @Override
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  @Override
  public boolean isDeleted() {
    return this.deleted;
  }
}
