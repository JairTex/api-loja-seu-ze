package br.com.seuze.store.api.exceptions;

public class InvalidProductException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidProductException(String message) {
		super(message);
	}
}