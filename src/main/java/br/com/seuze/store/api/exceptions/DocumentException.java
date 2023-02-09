package br.com.seuze.store.api.exceptions;

public class DocumentException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public DocumentException(String message) {
		super(message);
	}
}