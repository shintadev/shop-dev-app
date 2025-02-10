package com.shintadev.shop_dev_app.repository.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.product.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
