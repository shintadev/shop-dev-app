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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity implements SoftDeletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @CreationTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "create_at", updatable = false)
  private LocalDateTime createAt;

  @UpdateTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "update_at")
  private LocalDateTime updateAt;

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
