package com.shintadev.shop_dev_app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.user.PasswordResetToken;

import jakarta.persistence.LockModeType;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT t FROM PasswordResetToken t WHERE t.token = ?1")
  Optional<PasswordResetToken> findByToken(String token);
}
