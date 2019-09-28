package com.ecommerce.microcommerce.model;

import org.springframework.stereotype.Component;

import com.ecommerce.microcommerce.utils.EventType;

@Component
public class Event {

	private EventType eventType;

	private Product product;

	public Event() {
	}

	public Event(EventType eventType, Product product) {
		this.eventType = eventType;
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String toJSON() {
		return String.format("{eventeType: \"%s\", product: {id: %d, name: \"%s\", price: %d, purchasePrice: %d}}",
				eventType, product.getId(),
				product.getName(), product.getPrice(), product.getPurchasePrice());
	}
}
