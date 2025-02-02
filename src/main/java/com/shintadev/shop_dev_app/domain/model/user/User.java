package com.shintadev.shop_dev_app.domain.model.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.order.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "users",
    indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
        @Index(name = "idx_user_slug", columnList = "slug")
    })
@EqualsAndHashCode(callSuper = true)
@Builder
public class User extends BaseEntity {

  @NotNull
  @Column(name = "first_name", length = 128, nullable = false)
  private String firstName;

  @NotNull
  @Column(name = "last_name", length = 128, nullable = false)
  private String lastName;

  @NotNull
  @Column(name = "email", length = 128, nullable = false, unique = true)
  private String email;

  @Column(name = "phone", length = 16, nullable = true)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "varchar(16) default 'ACTIVE'")
  private UserStatus status;

  @Column(name = "slug", length = 128, nullable = false, unique = true)
  private String slug;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<Address> addresses = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude
  @Builder.Default
  private Set<Order> orders = new HashSet<>();
}
