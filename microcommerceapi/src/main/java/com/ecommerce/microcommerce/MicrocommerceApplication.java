package com.ecommerce.microcommerce;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.service.ProductService;

@SpringBootApplication
public class MicrocommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrocommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ProductService productService) {
		return (String[] args) -> {
			List<Product> products = productService.findAll().collectList().block();
			if (products == null || products.isEmpty()) {
				productService.save(new Product(null, "Ordinateur Portable", 350, 120), false);
				productService.save(new Product(null, "Aspirateur Robot", 500, 200), false);
				productService.save(new Product(null, "Table de ping pong", 750, 400), false);
				productService.findAll().collectList().block().forEach(product -> System.out.println(product));
			}
		};
	}

}
