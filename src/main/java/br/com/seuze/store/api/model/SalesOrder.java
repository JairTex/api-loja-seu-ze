package br.com.seuze.store.api.model;

import java.time.LocalDateTime;
import java.util.List;
import br.com.seuze.store.api.enumerations.SalesOrderStatusEnum;
import br.com.seuze.store.api.strategies.PaymentStrategy;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_order")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Data
public class SalesOrder{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "id")
	private Long id;
	
	@Column(name = "sale_id") 
	private Long sale_id;

	@OneToMany(cascade = CascadeType.ALL) 
	private List<ProductSalesOrder> bag;
	
	@Column(name = "total") 
	private double total;
	
	@Column(name = "document") 
	private String document;
	
	@Column(name = "date") 
	@Builder.Default
	private LocalDateTime date = LocalDateTime.now();
	
	@Enumerated
	@Column(name = "status")
	@Builder.Default
	private SalesOrderStatusEnum status = SalesOrderStatusEnum.OPEN;
	
	@OneToOne(cascade = CascadeType.ALL) 
	private PaymentStrategy payment;
}
