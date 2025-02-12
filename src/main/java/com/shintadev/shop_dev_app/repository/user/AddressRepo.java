package com.shintadev.shop_dev_app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.user.Address;

import jakarta.persistence.LockModeType;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT a FROM Address a WHERE a.id = ?1")
  Optional<Address> findByIdForUpdate(Long id);
}
