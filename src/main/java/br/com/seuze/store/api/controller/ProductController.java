package br.com.seuze.store.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.repository.ProductRepository;
import br.com.seuze.store.api.service.ProductService;

@RestController
@RequestMapping("seu-ze-store/")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	ProductRepository productRepository;
	
	
	@GetMapping("product/")
	public List<Product> getAllProducts(){
		return productService.listAllProducts();
	}
	
	@GetMapping("product/{sku}")
	public Product getProductBySku(@PathVariable String sku){
		return productService.searchBySku(sku);
	}
	
	@GetMapping("product/brand/{brand}")
	public List<Product> getProductByBrand(@PathVariable("brand") String brand){
		return productService.searchByBrand(ProductBrandEnum.valueOf(brand.toUpperCase()));
	}
	
	@GetMapping("product/category/{category}")
	public List<Product> getProductByCategory(@PathVariable("category") String category){
		return productService.searchByCategory(ProductCategoryEnum.valueOf(category.toUpperCase()));
	}
	
	@GetMapping("product/department/{department}")
	public List<Product> getProductByDepartment(@PathVariable("department") String department){
		return productService.searchByDepartment(ProductDepartmentEnum.valueOf(department.toUpperCase()));
	}
	
	@GetMapping("product/type/{type}")
	public List<Product> getProductByType(@PathVariable("type") String type){
		return productService.searchByType(ProductTypeEnum.valueOf(type.toUpperCase()));
	}
	
	@GetMapping("product/color/{color}")
	public List<Product> getProductByColor(@PathVariable("color") String color){
		return productService.searchByColor(ProductColorEnum.valueOf(color.toUpperCase()));
	}
	
	@GetMapping("product/size/{size}")
	public List<Product> getProductBySize(@PathVariable("size") int size){
		return productService.searchBySize(size);
	}
	
	@PostMapping("product/")
	public Product registerProduct(@RequestBody Product product) {
		return productService.register(product);
	}
	
	
	/*
	@DeleteMapping("product/{id}")
	public String deleteProduct(@PathVariable String sku){
		productRepository.deleteById(sku);
		return "Product deleted.";
	}*/
}
