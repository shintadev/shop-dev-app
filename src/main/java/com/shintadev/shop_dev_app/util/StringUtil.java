package com.shintadev.shop_dev_app.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {

  private StringUtil() {
  }

  public static boolean isVNMobile(String mobile) {
    String pMobile = "^(0[135789]\\d{8,9})$";
    return Pattern.matches(pMobile, mobile);
  }
}
