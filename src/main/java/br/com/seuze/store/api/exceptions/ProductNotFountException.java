package br.com.seuze.store.api.exceptions;

public class ProductNotFountException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ProductNotFountException(String message) {
		super(message);
	}
}
