package com.shintadev.shop_dev_app.repository.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.product.Product;

import jakarta.persistence.LockModeType;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  @Query("SELECT p FROM Product p WHERE " +
      "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
      "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
      "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
      "(:keyword IS NULL OR " +
      "(lower(p.name) LIKE lower(concat('%', :keyword, '%')) OR " +
      "lower(p.description) LIKE lower(concat('%', :keyword, '%'))) OR " +
      "(:status IS NULL OR p.status = :status))")
  Page<Product> findProductsByAdminFilters(
      @Param("categoryId") String categoryId,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("keyword") String keyword,
      @Param("status") String status,
      Pageable pageable);

  @Query("SELECT p FROM Product p WHERE " +
      "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
      "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
      "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
      "(:keyword IS NULL OR " +
      "(lower(p.name) LIKE lower(concat('%', :keyword, '%')) OR " +
      "lower(p.description) LIKE lower(concat('%', :keyword, '%')))) AND " +
      "p.status = 'ACTIVE'")
  Page<Product> findProductsByFilters(
      @Param("categoryId") String categoryId,
      @Param("minPrice") BigDecimal minPrice,
      @Param("maxPrice") BigDecimal maxPrice,
      @Param("keyword") String keyword,
      Pageable pageable);

  @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
  Page<Product> findPublicProducts(Pageable pageable);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT p FROM Product p WHERE p.id = ?1")
  Optional<Product> findByIdForUpdate(Long id);

  @Query("SELECT p FROM Product p WHERE " +
      "(lower(p.name) LIKE lower(concat('%', :keyword, '%')) OR " +
      "lower(p.description) LIKE lower(concat('%', :keyword, '%'))) AND " +
      "p.status = 'ACTIVE'")
  Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

  @Query("SELECT p FROM Product p WHERE p.slug = ?1 AND p.status = 'ACTIVE'")
  Optional<Product> findBySlug(String slug);

  boolean existsByName(String name);

  @Modifying
  @Query("UPDATE Product p SET p.stock = p.stock - :quantity WHERE p.id = :id AND p.stock >= :quantity")
  int updateStock(@Param("id") Long id, @Param("quantity") int quantity);

  @Query("SELECT p FROM Product p WHERE " +
      "p.category.id = (SELECT p2.category.id FROM Product p2 WHERE p2.slug = :slug) AND " +
      "p.slug <> :slug AND " +
      "p.status = 'ACTIVE'")
  List<Product> findRelatedProducts(@Param("slug") String slug, Pageable pageable);
}
