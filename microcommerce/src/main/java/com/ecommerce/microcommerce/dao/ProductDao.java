package com.ecommerce.microcommerce.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.microcommerce.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	List<Product> findAll();

	List<Product> findAllByOrderByNameAsc();

	Product findById(int id);

	Product save(Product product);
}
