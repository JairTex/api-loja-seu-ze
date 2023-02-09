package br.com.seuze.store.api.exceptions;

public class PixPaymentException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PixPaymentException(String message) {
		super(message);
	}
}
