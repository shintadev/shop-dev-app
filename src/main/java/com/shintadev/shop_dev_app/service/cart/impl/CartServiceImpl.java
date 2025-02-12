package com.shintadev.shop_dev_app.service.cart.impl;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.shintadev.shop_dev_app.domain.dto.request.cart.CartItemRequest;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartItemResponse;
import com.shintadev.shop_dev_app.domain.dto.response.cart.CartResponse;
import com.shintadev.shop_dev_app.domain.model.cart.Cart;
import com.shintadev.shop_dev_app.domain.model.cart.CartItem;
import com.shintadev.shop_dev_app.domain.model.product.Product;
import com.shintadev.shop_dev_app.domain.model.user.User;
import com.shintadev.shop_dev_app.exception.ResourceNotFoundException;
import com.shintadev.shop_dev_app.mapper.CartMapper;
import com.shintadev.shop_dev_app.repository.cart.CartRepo;
import com.shintadev.shop_dev_app.repository.product.ProductRepo;
import com.shintadev.shop_dev_app.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

  private final CartRepo cartRepo;

  private final CartMapper cartMapper;

  private final ProductRepo productRepo;

  @Override
  public Cart create(Cart cart) {
    return cartRepo.save(cart);
  }

  @Override
  public void delete(Long id) {
    cartRepo.deleteById(id);
  }

  @Override
  public CartItemResponse addToCart(CartItemRequest request) {
    // 1. Get the current user's cart
    Cart cart = getCartEntity();

    // 2. Check if the product already exists in the cart
    Product product = productRepo.findById(request.getProductId())
        .orElseThrow(() -> new ResourceNotFoundException("Product", "id", request.getProductId().toString()));

    Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
        .filter(item -> item.getProduct().getId().equals(product.getId()))
        .findFirst();

    CartItem cartItem;
    // 3. If the product exists, increment the quantity
    if (cartItemOpt.isPresent()) {
      cartItem = cartItemOpt.get();
      cartItem.setQuantity(cartItem.getQuantity() + 1);
    } else {
      // 4. If the product does not exist, add a new item
      cartItem = CartItem.builder()
          .cart(cart)
          .product(product)
          .quantity(request.getQuantity())
          .build();
      cart.getCartItems().add(cartItem);
    }

    // 5. Save the cart
    cartRepo.save(cart);

    return cartMapper.toResponse(cartItem);
  }

  @Override
  public CartItemResponse updateCartItem(CartItemRequest request) {
    // 1. Get the current user's cart
    Cart cart = getCartEntity();

    // 2. Check if the product exists in the cart
    CartItem item = cart.getCartItems().stream()
        .filter(ci -> ci.getProduct().getId().equals(request.getProductId()))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("CartItem", "productId", request.getProductId().toString()));

    // 3. Update the quantity
    item.setQuantity(request.getQuantity());

    // 4. Save the cart
    cartRepo.save(cart);

    return cartMapper.toResponse(item);
  }

  @Override
  public CartItemResponse removeFromCart(String productId) {
    // 1. Get the current user's cart
    Cart cart = getCartEntity();

    // 2. Check if the product exists in the cart
    CartItem item = cart.getCartItems().stream()
        .filter(ci -> ci.getProduct().getId().equals(productId))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("CartItem", "productId", productId));

    // 3. Decrement the quantity or remove the item
    if (item.getQuantity() > 1) {
      item.setQuantity(item.getQuantity() - 1);
    } else {
      cart.getCartItems().remove(item);
    }

    // 4. Save the cart
    cartRepo.save(cart);

    return cartMapper.toResponse(item);
  }

  @Override
  public CartResponse getCart() {
    Cart cart = getCartEntity();

    return cartMapper.toResponse(cart);
  }

  private Cart getCartEntity() {
    // 1. Get the current user
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // 2. Check if the cart exists, if not create a new one
    return cartRepo.findByUserId(user.getId())
        .orElseGet(() -> create(Cart.builder().user(user).build()));
  }
}
