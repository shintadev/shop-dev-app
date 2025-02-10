package com.shintadev.shop_dev_app.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.shintadev.shop_dev_app.domain.model.product.Product;

@Component
public class StringUtil {

  public static boolean isVNMobile(String mobile) {
    String pMobile = "^(0[135789]\\d{8,9})$";
    return Pattern.matches(pMobile, mobile);
  }

  public static boolean isEmail(String email) {
    String pEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    return Pattern.matches(pEmail, email);
  }

  public static String generateSlug(Product product) {
    return product.getName().toLowerCase() + "-" + product.getId();
  }
}
