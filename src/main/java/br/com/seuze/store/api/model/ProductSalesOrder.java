package br.com.seuze.store.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "product_sales_order")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductSalesOrder {
	@JsonIgnore
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	@JsonIgnore
	@Column(name = "order_id", nullable = false) 
	private Long orderId;
	
	@JsonIgnore
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
	
	@JsonIgnore
	@Column(name = "is_sold") 
	@Builder.Default
	private boolean isSold = false;

	public ProductSalesOrder(String sku) {
		this.sku = sku;
	}
	
}
