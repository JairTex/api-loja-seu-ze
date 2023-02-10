package br.com.seuze.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.service.ProductService;
import br.com.seuze.store.api.service.SalesOrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("seu-ze-store/")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	SalesOrderService salesOrderService; 

	@PostMapping("product/")
	public ResponseEntity<?> registerProduct(@RequestBody @Valid Product product) {
			return new ResponseEntity<>(productService.registerProduct(product),
					HttpStatus.CREATED);
		
	}
	
	@GetMapping("products/")
	public ResponseEntity<?> getAllProducts(){
			return new ResponseEntity<>(productService.listAllProducts(),
						HttpStatus.OK);
	}
	
	@GetMapping("product/{sku}")
	public ResponseEntity<?> getProductBySku(@PathVariable String sku){
			return new ResponseEntity<>(productService.searchBySku(sku),
						HttpStatus.OK);
	}
	
	@GetMapping("product/brand/{brand}")
	public ResponseEntity<?> getProductByBrand(@PathVariable("brand") String brand){
		return new ResponseEntity<>(
						productService.searchByBrand(ProductBrandEnum.valueOf(brand.toUpperCase())),
						HttpStatus.OK);
	}
	
	@GetMapping("product/category/{category}")
	public ResponseEntity<?> getProductByCategory(@PathVariable("category") String category){
		return new ResponseEntity<>(
						productService.searchByCategory(ProductCategoryEnum.valueOf(category.toUpperCase())),
						HttpStatus.OK);
	}
	
	@GetMapping("product/department/{department}")
	public ResponseEntity<?> getProductByDepartment(@PathVariable("department") String department){
		return new ResponseEntity<>(
						productService.searchByDepartment(ProductDepartmentEnum.valueOf(department.toUpperCase())),
						HttpStatus.OK);
	}
	
	@GetMapping("product/type/{type}")
	public ResponseEntity<?> getProductByType(@PathVariable("type") String type){
		return new ResponseEntity<>(
						productService.searchByType(ProductTypeEnum.valueOf(type.toUpperCase())),
						HttpStatus.OK);
		
	}
	
	@GetMapping("product/color/{color}")
	public ResponseEntity<?> getProductByColor(@PathVariable("color") String color){
		return new ResponseEntity<>(
						productService.searchByColor(ProductColorEnum.valueOf(color.toUpperCase())),
						HttpStatus.OK);
	}
	
	@GetMapping("product/size/{size}")
	public ResponseEntity<?> getProductBySize(@PathVariable("size") int size){
		return new ResponseEntity<>(
						productService.searchBySize(size),
						HttpStatus.OK);
	}
	
	@PostMapping("product/add-to-sales-order/{orderId}")
	public ResponseEntity<?> addProductToSalesOrder(@PathVariable Long orderId, 
			@RequestBody ObjectNode objectNode) {
		String sku = objectNode.get("sku").asText();
		Integer amount = Integer.parseInt(objectNode.get("amount").asText());
			return new ResponseEntity<>(productService.addProductToSalesOrder(sku, orderId, amount),
					HttpStatus.OK);
		
	}
	
	@PostMapping("product/remove-from-sales-order/{orderId}")
	public ResponseEntity<?> removeProductFromSalesOrder(@PathVariable Long orderId, @RequestBody ObjectNode objectNode) {
		String sku = objectNode.get("sku").asText();
		Integer amount = Integer.parseInt(objectNode.get("amount").asText());
		
			return new ResponseEntity<>(productService.removeProductFromSalesOrder(sku, orderId, amount),
					HttpStatus.OK);
	}
}
