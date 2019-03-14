package com.ecommerce.microcommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProduitGratuitException extends Exception {
	
	private static final long serialVersionUID = 2416746131327077815L;

	public ProduitGratuitException(String message) {
		super(message);
	}
}
