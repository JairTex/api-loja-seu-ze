package br.com.seuze.store.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sales")
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor 
public class Sale {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id")
	private Long id;
	
	@Column(name = "date") 
	@Builder.Default
	private LocalDateTime date = LocalDateTime.now();
	
	@OneToOne(cascade = CascadeType.ALL) 
	private SalesOrder salesOrder;
	
}
