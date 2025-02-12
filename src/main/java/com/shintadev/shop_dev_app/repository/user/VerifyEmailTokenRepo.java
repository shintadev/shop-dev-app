package com.shintadev.shop_dev_app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.user.VerifyEmailToken;

import jakarta.persistence.LockModeType;

@Repository
public interface VerifyEmailTokenRepo extends JpaRepository<VerifyEmailToken, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT v FROM VerifyEmailToken v INNER JOIN User u ON v.user.id = u.id WHERE u.id = ?1")
  Optional<VerifyEmailToken> findByUserId(Long userId);
}
