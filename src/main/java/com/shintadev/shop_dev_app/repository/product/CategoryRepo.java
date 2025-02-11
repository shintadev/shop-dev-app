package com.shintadev.shop_dev_app.repository.product;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.product.Category;

import jakarta.persistence.LockModeType;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

  List<Category> findByParentIsNull();

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT c FROM Category c WHERE c.id = ?1")
  Optional<Category> findByIdForUpdate(Long id);

  boolean existsByName(String name);
}
