package br.com.seuze.store.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaleNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SaleNotFoundException(String message) {
		super(message);
	}
}