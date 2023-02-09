package br.com.seuze.store.api.strategies;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.seuze.store.api.enumerations.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public abstract class PaymentStrategy { 
	@JsonIgnore
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	@JsonIgnore
	@Column(name = "order_id", nullable = false) 
	private Long orderId;
	
	@JsonIgnore
	@Column(name = "sale_id") 
	private Long saleId;
	
	@Column(name = "total") 
	private Double total;
	
	public abstract PaymentMethod getPaymentMethod();
}
