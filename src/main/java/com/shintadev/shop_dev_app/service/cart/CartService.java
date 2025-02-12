package com.shintadev.shop_dev_app.service.cart;

import com.shintadev.shop_dev_app.domain.dto.request.cart.CartItemRequest;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartItemResponse;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartResponse;
import com.shintadev.shop_dev_app.domain.model.cart.Cart;

public interface CartService {

  Cart create(Cart request);

  void delete(Long id);

  CartItemResponse addToCart(CartItemRequest request);

  CartItemResponse updateCartItem(CartItemRequest request);

  CartItemResponse removeFromCart(String productId);

  CartResponse getCart();
}
