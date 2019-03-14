package com.ecommerce.microcommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Length(min=3, max=20)
	private String name;

	private int price;
	
	private int purchasePrice;
	
	public Product() {
	}
	
	public Product(int id, String name, int price, int purchasePrice) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.purchasePrice = purchasePrice;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Override
	public String toString() {
		return "Product{id=" + id + ", nom='" + name + "', prix=" + price + "}";
	}
}
