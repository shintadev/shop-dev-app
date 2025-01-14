package com.shintadev.shop_dev_app.repository;

import com.shintadev.shop_dev_app.model.Product;

public interface ProductRepo {
  Product findByName(String name);

}
