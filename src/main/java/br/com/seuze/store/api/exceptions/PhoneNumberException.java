package br.com.seuze.store.api.exceptions;

public class PhoneNumberException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public PhoneNumberException(String message) {
		super(message);
	}
}