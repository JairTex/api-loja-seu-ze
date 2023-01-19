package br.com.seuze.store.api.model;

import java.util.Calendar;
import java.util.List;
import br.com.seuze.store.api.enumerations.SalePaymentEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_order")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor 
public class SalesOrder {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	//@OneToMany(cascade = CascadeType.ALL) 

	@OneToMany(cascade = CascadeType.ALL) 
	private List<ProductSold> bag;
	
	@Column(name = "total", nullable = false) 
	private double total;
	
	@Column(name = "document") 
	private String document;
	
	@Column(name = "date", insertable=false) 
	@Builder.Default
	private Calendar date = Calendar.getInstance();
	
	@Enumerated
	@Column(name = "payment", nullable = false) 
	private SalePaymentEnum payment;
	
	@Column(name = "is_completed") 
	@Builder.Default
	private boolean isCompleted = false;
}
