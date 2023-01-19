package br.com.seuze.store.api.strategies;

  public class CashPaymentStrategy implements PaymentStrategyStrategy { 
 
	private Double amount;
  
	  public CashPaymentStrategy(Double amount) { 
		  this.amount = amount; 
		  }
  
	  @Override
	  public String description() {
		  return "Payment: Cash";
	  }
  
	  public Double getAmount() { 
		  return amount; } 
	  public void setAmount(Double amount) { 
		  this.amount = amount; } 
  }
 