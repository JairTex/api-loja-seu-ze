package br.com.seuze.store.api.strategies;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class CardPaymentStrategy extends PaymentStrategy {
	@Column(name = "card_security") 
	String cardSecurity;
	@Column(name = "card_number") 
	String cardNumber;
	@Column(name = "expiration_date") 
	String expirationDate;
}
