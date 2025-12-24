package com.ecommerce.vn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VnApplication {

	public static void main(String[] args) {
		SpringApplication.run(VnApplication.class, args);
	}

}
