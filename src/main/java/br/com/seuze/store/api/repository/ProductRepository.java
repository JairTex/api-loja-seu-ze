package br.com.seuze.store.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	@Query(value = "select p from Product p where p.brand like ?1")
	List<Product> searchByBrand(ProductBrandEnum brand);
	
	@Query(value = "select p from Product p where p.category like ?1")
	List<Product> searchByCategory(ProductCategoryEnum category);
	
	@Query(value = "select p from Product p where p.department like ?1")
	List<Product> searchByDepartment(ProductDepartmentEnum department);
	
	@Query(value = "select p from Product p where p.type like ?1")
	List<Product> searchByType(ProductTypeEnum type);
	
	@Query(value = "select p from Product p where p.color like ?1")
	List<Product> searchByColor(ProductColorEnum color);
	
	@Query(value = "select p from Product p where p.size like ?1")
	List<Product> searchBySize(int size);
}
