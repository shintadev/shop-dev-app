package com.shintadev.shop_dev_app.domain.model.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "verify_email_tokens")
@Builder
public class VerifyEmailToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Column(name = "token", nullable = false)
  private String token;

  @NotNull
  @Column(name = "expiry_time", nullable = false)
  private LocalDateTime expiryTime;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
