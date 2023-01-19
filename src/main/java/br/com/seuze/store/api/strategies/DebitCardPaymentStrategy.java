package br.com.seuze.store.api.strategies;

public class DebitCardPaymentStrategy extends CardPaymentStrategy  {
	public DebitCardPaymentStrategy(String cardSecurity, String cardNumber, String expirationDate) {
		super(cardSecurity, cardNumber, expirationDate);
	}

	@Override
	public String toString() {
		return "Debit Card";
	}
	@Override
	public String description() {
		return "Payment: Debit Card";
	}
}