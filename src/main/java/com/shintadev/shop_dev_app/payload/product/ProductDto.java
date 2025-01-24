package com.shintadev.shop_dev_app.payload.product;

import java.util.List;

import lombok.Data;

@Data
public class ProductDto {
  private String name;
  private double price;
  private int stock;
  private String description;
  private String slug;
  private List<String> productMediaUrls;
}
