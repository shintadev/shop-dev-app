package com.shintadev.shop_dev_app.domain.model.user;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shintadev.shop_dev_app.base.BaseEntity;
import com.shintadev.shop_dev_app.domain.enums.user.UserRole;
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
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name = "users",
    indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true),
    })
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

  @NotNull
  @Column(name = "first_name", length = 64, nullable = false)
  private String firstName;

  @NotNull
  @Column(name = "last_name", length = 64, nullable = false)
  private String lastName;

  @Column(name = "display_name", length = 128)
  private String displayName;

  @NotNull
  @Column(name = "email", length = 128, nullable = false, unique = true)
  private String email;

  @Column(name = "password", length = 36, nullable = false)
  private String password;

  @Column(name = "phone", length = 16)
  private String phone;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, columnDefinition = "varchar(16) default 'ACTIVE'")
  @Builder.Default
  private UserStatus status = UserStatus.INACTIVE;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  @Builder.Default
  private UserRole role = UserRole.ROLE_USER;

  @Column(name = "avatar_url", length = 256)
  private String avatarUrl;

  @Column(name = "verification_code")
  private String verificationCode;

  @Column(name = "verification_code_expiration_at")
  private LocalDateTime verificationCodeExpirationAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<Address> addresses = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude
  @Builder.Default
  private Set<Order> orders = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.status != UserStatus.SUSPENDED;
  }

  @Override
  public boolean isEnabled() {
    return this.status == UserStatus.ACTIVE;
  }
}
