package com.shintadev.shop_dev_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.shintadev.shop_dev_app") // @Autowired
@EntityScan("com.shintadev.shop_dev_app.model") // @Entity
@EnableJpaRepositories("com.shintadev.shop_dev_app.repository") // @Repository
public class ShopDevAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopDevAppApplication.class, args);
	}

}
