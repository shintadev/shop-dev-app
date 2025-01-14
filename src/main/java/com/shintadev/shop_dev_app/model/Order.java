package com.shintadev.shop_dev_app.model;

import com.shintadev.shop_dev_app.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "orders")
@EqualsAndHashCode(callSuper = false)
public class Order extends BaseEntity {

}
