package br.com.seuze.store.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	List<Product> findBySku(String sku);
	
	//@Query(value = "select p from Product p where p.brand like ?1")
	List<Product> findByBrand(ProductBrandEnum brand);
	
	//@Query(value = "select p from Product p where p.category like ?1")
	List<Product> findByCategory(ProductCategoryEnum category);
	
	//@Query(value = "select p from Product p where p.department like ?1")
	List<Product> findByDepartment(ProductDepartmentEnum department);
	
	//@Query(value = "select p from Product p where p.type like ?1")
	List<Product> findByType(ProductTypeEnum type);
	
	//@Query(value = "select p from Product p where p.color like ?1")
	List<Product> findByColor(ProductColorEnum color);
	
	//@Query(value = "select p from Product p where p.size like ?1")
	List<Product> findBySize(int size);
}
