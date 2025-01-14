package com.shintadev.shop_dev_app.model;

import com.shintadev.shop_dev_app.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;
}
