package com.ecommerce.microcommerce.service;

import com.ecommerce.microcommerce.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
	Flux<Product> findAll();

	Flux<Product> findAllByOrderByNameAsc();

	Mono<Product> findById(long id);

	Mono<Product> save(Product product, boolean sendEvent);

	Mono<Void> delete(Product product);
}
