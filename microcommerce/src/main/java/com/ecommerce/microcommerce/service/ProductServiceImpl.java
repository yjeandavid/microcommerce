package com.ecommerce.microcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Flux<Product> findAll() {
		return Flux.fromIterable(productDao.findAll());
	}

	@Override
	public Flux<Product> findAllByOrderByNameAsc() {
		return Flux.fromIterable(productDao.findAllByOrderByNameAsc());
	}

	@Override
	public Mono<Product> findById(int id) {
		return Mono.justOrEmpty(productDao.findById(id));
	}

	@Override
	public Mono<Product> save(Product product) {
		return Mono.just(productDao.save(product));
	}

	@Override
	public Mono<Void> delete(Product product) {
		productDao.delete(product);
		return Mono.empty();
	}
}
