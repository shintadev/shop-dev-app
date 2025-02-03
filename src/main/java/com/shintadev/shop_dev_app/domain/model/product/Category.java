package com.shintadev.shop_dev_app.domain.model.product;

import java.util.HashSet;
import java.util.Set;

import com.shintadev.shop_dev_app.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "categories")
@EqualsAndHashCode(callSuper = false)
@Builder
public class Category extends BaseEntity{

  @NotNull
  @Column(name = "name", length = 128, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  @Builder.Default
  private Set<Category> subcategories = new HashSet<>();
}
