package br.com.seuze.store.api.exceptions;

public class InvalidSalesOrderException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidSalesOrderException(String message) {
		super(message);
	}
}