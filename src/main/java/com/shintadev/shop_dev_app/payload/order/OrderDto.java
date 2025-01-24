package com.shintadev.shop_dev_app.payload.order;

import java.util.List;

import com.shintadev.shop_dev_app.payload.product.ProductDto;
import com.shintadev.shop_dev_app.payload.user.UserDto;

import lombok.Data;

@Data
public class OrderDto {
  private double totalPrice;
  private List<ProductDto> products;
  private UserDto user;
}
