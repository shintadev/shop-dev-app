package com.shintadev.shop_dev_app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.enums.user.UserStatus;
import com.shintadev.shop_dev_app.domain.model.user.User;

import jakarta.persistence.LockModeType;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT u FROM User u WHERE u.id = ?1")
  Optional<User> findByIdForUpdate(Long id);

  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findByEmail(String email);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT u FROM User u WHERE u.email = ?1")
  Optional<User> findByEmailForUpdate(String email);

  @Query("SELECT u FROM User u WHERE u.slug = ?1")
  Optional<User> findBySlug(String slug);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT u FROM User u WHERE u.slug = ?1")
  Optional<User> findBySlugForUpdate(String slug);

  boolean existsByEmail(String email);

  @Modifying
  @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
  int updateStatus(@Param("id") Long id, @Param("status") UserStatus status);
}
