package br.com.seuze.store.api.strategies;

import br.com.seuze.store.api.enumerations.PaymentMethod;
import br.com.seuze.store.api.enumerations.PixKeyTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PixPaymentStrategy extends PaymentStrategy { 	
	@Enumerated
	@Column(name = "pix_key_type")
	private PixKeyTypeEnum pixKeyType;
	
	@Column(name = "pix_key") 
	private String key;

	@Override
	public PaymentMethod getPaymentMethod() {
		return PaymentMethod.PIX;
	}
	
}
