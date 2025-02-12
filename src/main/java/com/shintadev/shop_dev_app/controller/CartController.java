package com.shintadev.shop_dev_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shintadev.shop_dev_app.domain.dto.request.cart.CartItemRequest;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartItemResponse;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartResponse;
import com.shintadev.shop_dev_app.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/add")
  public ResponseEntity<CartItemResponse> addToCart(@RequestBody CartItemRequest cart) {
    return ResponseEntity.ok(cartService.addToCart(cart));
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/update")
  public ResponseEntity<CartItemResponse> updateCart(@RequestBody CartItemRequest request) {
    return ResponseEntity.ok(cartService.updateCartItem(request));
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/remove")
  public ResponseEntity<CartItemResponse> removeFromCart(@RequestParam String productId) {
    return ResponseEntity.ok(cartService.removeFromCart(productId));
  }

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/get")
  public ResponseEntity<CartResponse> getCart() {
    return ResponseEntity.ok(cartService.getCart());
  }
}
