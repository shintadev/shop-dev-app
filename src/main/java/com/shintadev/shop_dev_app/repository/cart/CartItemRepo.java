package com.shintadev.shop_dev_app.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shintadev.shop_dev_app.domain.model.cart.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
