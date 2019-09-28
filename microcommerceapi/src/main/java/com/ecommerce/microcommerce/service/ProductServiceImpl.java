package com.ecommerce.microcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Event;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.utils.EventType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private Publisher publisher;

	@Override
	public Flux<Product> findAll() {
		return Flux.fromIterable(productDao.findAll());
	}

	@Override
	public Flux<Product> findAllByOrderByNameAsc() {
		return Flux.fromIterable(productDao.findAllByOrderByNameAsc());
	}

	@Override
	public Mono<Product> findById(long id) {
		return Mono.justOrEmpty(productDao.findById(id));
	}

	@Override
	public Mono<Product> save(Product product, boolean sendEvent) {
		boolean addEvent = product.getId() == null;
		Product savedProduct = save(product);
		if (sendEvent) {
			publisher.publishEvent(new Event(addEvent ? EventType.ADD : EventType.EDIT, savedProduct));
		}
		return Mono.just(savedProduct);
	}

	private Product save(Product product) {
		return productDao.save(product);
	}

	@Override
	public Mono<Void> delete(Product product) {
		productDao.delete(product);
		publisher.publishEvent(new Event(EventType.DELETE, product));
		return Mono.empty();
	}
}
