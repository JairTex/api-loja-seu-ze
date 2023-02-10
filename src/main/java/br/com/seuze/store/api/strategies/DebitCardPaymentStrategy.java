package br.com.seuze.store.api.strategies;

import br.com.seuze.store.api.enumerations.PaymentMethodEnum;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class DebitCardPaymentStrategy extends CardPaymentStrategy  {@Override
	public PaymentMethodEnum getPaymentMethod() {
		return PaymentMethodEnum.DEBIT_CARD;
	}
	
}