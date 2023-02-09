package br.com.seuze.store.api.exceptions;

public class CardSecurityException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CardSecurityException(String message) {
		super(message);
	}
}