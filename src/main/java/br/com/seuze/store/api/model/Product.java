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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	
	@Column(name = "description") 
	@NotEmpty (message = "Description is mandatory!")
	private String description;
	
	@Enumerated
	@NotNull (message = "Brand is mandatory!")
	@Column(name = "brand") 
	private ProductBrandEnum brand;
	
	@Enumerated
	@NotNull (message = "Category is mandatory!")
	@Column(name = "category") 
	private ProductCategoryEnum category;
	
	@Enumerated
	@NotNull (message = "Department is mandatory!")
	@Column(name = "department") 
	private ProductDepartmentEnum department;
	
	@Enumerated
	@NotNull (message = "Type is mandatory!")
	@Column(name = "type") 
	private ProductTypeEnum type;
	
	@Enumerated
	@NotNull (message = "Color is mandatory!")
	@Column(name = "color") 
	private ProductColorEnum color;
	
	@Column(name = "size") 
	private String size;
	
	@Column(name = "amount") 
	private int amount;
	
	@Column(name = "unitprice") 
	private double unitprice;
	
}
