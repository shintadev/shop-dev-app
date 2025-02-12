package com.shintadev.shop_dev_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shintadev.shop_dev_app")
public class ShopDevAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopDevAppApplication.class, args);
	}

}
