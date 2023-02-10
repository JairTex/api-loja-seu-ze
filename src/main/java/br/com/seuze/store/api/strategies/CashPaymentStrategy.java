package br.com.seuze.store.api.strategies;

import br.com.seuze.store.api.enumerations.PaymentMethodEnum;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class CashPaymentStrategy extends PaymentStrategy { 
	@Override
	public PaymentMethodEnum getPaymentMethod() {
		return PaymentMethodEnum.CASH;
	}
}
 