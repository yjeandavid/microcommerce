package com.ecommerce.microcommerce.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.exception.ProduitGratuitException;
import com.ecommerce.microcommerce.model.Product;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "API pour les opérations CRUD sur les produits.")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductDao productDao;
	
	@ApiOperation(value = "Récupère la liste des produits disponibles. Possible d'afficher en ordre croissant de nom en ajoutant le paramètre sort")
	@GetMapping
	@CrossOrigin
	public MappingJacksonValue getProducts(@RequestParam(name="sort", defaultValue="false", required = false) boolean sort) {
		Iterable<Product> products;
		SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAll();
		
		if (sort) {
			products = productDao.findAllByOrderByNameAsc();
			// monFiltre = SimpleBeanPropertyFilter.serializeAll();
		} else {
			products = productDao.findAll();
			// monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice");
		}
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue productsFiltered = new MappingJacksonValue(products);
		productsFiltered.setFilters(filters);
		return productsFiltered;
	}
	
	@ApiOperation(value = "Récupère un produit à condition que celui-ci soit dans les stocks.")
	@GetMapping("{id}")
	@CrossOrigin
	public ResponseEntity<MappingJacksonValue> getProduct(@PathVariable int id) {
		Product product = productDao.findById(id);
		
		if (product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MappingJacksonValue("Product with id=" + id + " not found"));
		} else {
			// SimpleBeanPropertyFilter monFiltre =
			// SimpleBeanPropertyFilter.serializeAllExcept("purchasePrice");
			SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAll();
			FilterProvider filters = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
			MappingJacksonValue productFiltered = new MappingJacksonValue(product);
			productFiltered.setFilters(filters);
			return ResponseEntity.ok(productFiltered);
		} 
	}
	
	@ApiOperation(value = "Met à jour l'information d'un produit (s'il existe).")
	@PutMapping("{id}")
	@CrossOrigin
	public ResponseEntity<Void> updateProduct(@PathVariable int id, @RequestBody Product product) {
		productDao.save(product);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Ajoute un produit à la liste des produits disponibles.")
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Void> addProduct(@Valid @RequestBody Product product) throws ProduitGratuitException {
		if (product.getPrice() <= 0) {
			throw new ProduitGratuitException("The price can't be lessier than or equal to 0.");
		}

		Product productAdded = productDao.save(product);

		if (productAdded == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productAdded.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@ApiOperation(value = "Supprime un produit existant.")
	@DeleteMapping("{id}")
	@CrossOrigin
	public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
		productDao.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
