package com.ecommerce.microcommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;

import io.swagger.annotations.Api;

@Api(description="Obtenir la marge de chaque produit.")
@RestController
@RequestMapping("/adminproduits")
public class AdminProduitController {

	@Autowired
	private ProductDao productDao;
	
	@GetMapping
	public ResponseEntity<Object> calculerMargeProduit() {
		List<Product> products = productDao.findAll();

		if (products.isEmpty()) {
			return ResponseEntity.ok(ResponseEntity.EMPTY);
		}

		Map<String, Integer> results = new HashMap<>();
		for (Product p : products) {
			results.put(p.toString(),p.getPrice()-p.getPurchasePrice());
		}
		return ResponseEntity.ok(results);
	}
}
