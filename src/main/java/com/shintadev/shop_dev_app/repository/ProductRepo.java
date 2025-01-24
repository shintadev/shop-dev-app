package com.shintadev.shop_dev_app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.model.product.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

  Page<Product> findByNameLike(String name, Pageable pageable);

  Product findBySlug(String slug);
}
