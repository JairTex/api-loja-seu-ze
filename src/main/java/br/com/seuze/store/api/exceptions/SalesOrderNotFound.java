package br.com.seuze.store.api.exceptions;

public class SalesOrderNotFound extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public SalesOrderNotFound(String message) {
		super(message);
	}
}