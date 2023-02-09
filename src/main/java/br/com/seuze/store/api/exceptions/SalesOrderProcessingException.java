package br.com.seuze.store.api.exceptions;

public class SalesOrderProcessingException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SalesOrderProcessingException(String message) {
		super(message);
	}
}