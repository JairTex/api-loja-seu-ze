package br.com.seuze.store.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_sold")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductSold {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	@Column(name = "order_id", nullable = false) 
	private Long orderId;
	
	@Column(name = "sale_id") 
	private Long saleId;
	
	@Column(name = "sku")
	private String sku;
	
	@Column(name = "amount") 
	private int amount;
	
	@Column(name = "unitprice") 
	private double unitprice;
	
	@Column(name = "price") 
	private double price;
	
	@Column(name = "is_sold") 
	@Builder.Default
	private boolean isSold = false;

	public ProductSold(String sku) {
		this.sku = sku;
	}
	
}
