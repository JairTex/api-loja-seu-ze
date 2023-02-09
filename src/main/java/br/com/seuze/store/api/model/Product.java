package br.com.seuze.store.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_products")
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class Product{
	@JsonIgnore
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	
	@Column(name = "sku", unique = true)
	private String sku;
	
	@Column(name = "description", nullable = false) 
	private String description;
	
	@Enumerated
	@Column(name = "brand", nullable = false) 
	private ProductBrandEnum brand;
	
	@Enumerated
	@Column(name = "category", nullable = false) 
	private ProductCategoryEnum category;
	
	@Enumerated
	@Column(name = "department", nullable = false) 
	private ProductDepartmentEnum department;
	
	@Enumerated
	@Column(name = "type", nullable = false) 
	private ProductTypeEnum type;
	
	@Enumerated
	@Column(name = "color", nullable = false) 
	private ProductColorEnum color;
	
	@Column(name = "size", nullable = false) 
	private int size;
	
	@Column(name = "amount") 
	private int amount;
	
	@Column(name = "unitprice") 
	private double unitprice;
	
	public Product(String description, ProductBrandEnum brand, ProductCategoryEnum category,
			ProductDepartmentEnum department, ProductTypeEnum type, ProductColorEnum color, 
			int size, int amount, double unitprice) {
		
		this.sku = brand.getValue() + "-" + category.getValue() + department.getValue()
		+ "-" + type.getValue() + "-" + color.getValue() + "-" + size;
		this.description = description;
		this.brand = brand;
		this.category = category;
		this.department = department;
		this.type = type;
		this.color = color;
		this.size = size;
		this.amount = amount;
		this.unitprice = unitprice;
	}
	
}
