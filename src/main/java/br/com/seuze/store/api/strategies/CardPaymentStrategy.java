package br.com.seuze.store.api.strategies;

abstract class CardPaymentStrategy implements PaymentStrategyStrategy {
	String cardSecurity;
	String cardNumber;
	String expirationDate;
	
	public CardPaymentStrategy(String cardSecurity, String cardNumber, String expirationDate) {
		this.cardSecurity = cardSecurity;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
	}

	@Override
	public abstract String description();
	
	public String getCardSecurity() {
		return cardSecurity;
	}
	public void setCardSecurity(String cardSecurity) {
		this.cardSecurity = cardSecurity;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
}
