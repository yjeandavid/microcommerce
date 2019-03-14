package com.ecommerce.microcommerce.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.ecommerce.microcommerce.exception.ProduitGratuitException;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Api(description = "API pour les opérations CRUD sur les produits.")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Récupère la liste des produits disponibles. Possible d'afficher en ordre croissant de nom en ajoutant le paramètre sort")
	@GetMapping
	@CrossOrigin
	public Flux<Product> getProducts(
			@RequestParam(name = "sort", defaultValue = "false", required = false) boolean sort) {
		return sort ? productService.findAllByOrderByNameAsc() : productService.findAll();
	}
	
	@ApiOperation(value = "Récupère un produit à condition que celui-ci soit dans les stocks.")
	@GetMapping("{id}")
	@CrossOrigin
	public Mono<ResponseEntity<Product>> getProduct(@PathVariable int id) {
		return productService.findById(id).map(product -> ResponseEntity.ok(product))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Met à jour l'information d'un produit (s'il existe).")
	@PutMapping("{id}")
	@CrossOrigin
	public Mono<ResponseEntity<Void>> updateProduct(@PathVariable int id, @RequestBody Product product) {
		return productService.findById(id).flatMap(existingProduct -> {
			existingProduct.setName(product.getName());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setPurchasePrice(product.getPurchasePrice());
			return productService.save(existingProduct);
		}).map(updatedProduct -> ResponseEntity.noContent().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Ajoute un produit à la liste des produits disponibles.")
	@PostMapping
	@CrossOrigin
	public Mono<ResponseEntity<Void>> addProduct(@Valid @RequestBody Product product) throws ProduitGratuitException {
		if (product.getPrice() <= 0) {
			throw new ProduitGratuitException("The price can't be lessier than or equal to 0.");
		}
		return productService.save(product).map(productAdded -> {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(product.getId()).toUri();
			return ResponseEntity.created(location).<Void>build();
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
	}

	@ApiOperation(value = "Supprime un produit existant.")
	@DeleteMapping("{id}")
	@CrossOrigin
	public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable int id) {
		return productService.findById(id)
				.flatMap(existingProduct -> productService.delete(existingProduct)
						.then(Mono.just(ResponseEntity.noContent().<Void>build())))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
