package br.com.seuze.store.api.model;

import java.util.Calendar;
import java.util.List;

import br.com.seuze.store.api.enumerations.SalePaymentEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "sale")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor 
public class Sale {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	@Column(name = "order_id", nullable = false) 
	private Long orderId;

	@OneToMany(cascade = CascadeType.ALL) 
	private List<ProductSold> bag;
	
	@Column(name = "total", nullable = false) 
	private double total;
	
	@Column(name = "document") 
	private String document;
	
	@Column(name = "date", insertable=false) 
	@Builder.Default
	private Calendar date = Calendar.getInstance();
	
	@Column(name = "payment", nullable = false) 
	private SalePaymentEnum payment;
	
}
