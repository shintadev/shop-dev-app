package com.shintadev.shop_dev_app.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.cart.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

}
