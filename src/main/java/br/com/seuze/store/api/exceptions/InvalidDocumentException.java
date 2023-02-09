package br.com.seuze.store.api.exceptions;

public class InvalidDocumentException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidDocumentException(String message) {
		super(message);
	}
}