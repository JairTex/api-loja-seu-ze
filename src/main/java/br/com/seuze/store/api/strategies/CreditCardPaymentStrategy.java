package br.com.seuze.store.api.strategies;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CreditCardPaymentStrategy extends CardPaymentStrategy { 
   
	public CreditCardPaymentStrategy(String cardSecurity, String cardNumber, String expirationDate) {
		super(cardSecurity, cardNumber, expirationDate);
	}
	@Override
	public String description() {
		return "Payment: Credit Card";
	}
}
