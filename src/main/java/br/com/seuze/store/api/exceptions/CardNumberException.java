package br.com.seuze.store.api.exceptions;

public class CardNumberException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CardNumberException(String message) {
		super(message);
	}
}