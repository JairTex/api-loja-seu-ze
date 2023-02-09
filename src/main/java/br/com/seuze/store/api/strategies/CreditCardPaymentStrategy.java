package br.com.seuze.store.api.strategies;

import br.com.seuze.store.api.enumerations.PaymentMethod;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class CreditCardPaymentStrategy extends CardPaymentStrategy {@Override
	public PaymentMethod getPaymentMethod() {
		return PaymentMethod.CREDIT_CARD;
	} 
	
}
