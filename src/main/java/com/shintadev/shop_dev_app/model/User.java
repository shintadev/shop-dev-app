package com.shintadev.shop_dev_app.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity {

  @Column(name = "name", length = 128, nullable = false)
  private String name;

  @Column(name = "email", length = 128, nullable = false, unique = true)
  private String email;

  @Column(name = "address", length = 255, nullable = true)
  private String address;

  @Column(name = "phone", length = 16, nullable = true)
  private String phone;

  @Column(name = "username", length = 128, nullable = false, unique = true)
  private String username;

  @Column(name = "password", length = 128, nullable = false)
  private String password;
}
