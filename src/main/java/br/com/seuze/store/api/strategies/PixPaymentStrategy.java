package br.com.seuze.store.api.strategies;

public class PixPaymentStrategy implements PaymentStrategyStrategy { 
	private String key;
	private Double amount;
	
	public PixPaymentStrategy(String key, Double amount) {
		this.key = key;
		this.amount = amount;
	}

	@Override
	public String description() {
		return "Payment: Pix";
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
