package com.shintadev.shop_dev_app.base;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shintadev.shop_dev_app.common.SoftDeletable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity implements SoftDeletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @CreationTimestamp
  @Column(updatable = false)
  private Date createAt;

  @UpdateTimestamp
  @Column()
  private Date updateAt;

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
