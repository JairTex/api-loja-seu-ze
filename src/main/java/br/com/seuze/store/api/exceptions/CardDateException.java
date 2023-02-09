package br.com.seuze.store.api.exceptions;

public class CardDateException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public CardDateException(String message) {
		super(message);
	}
}