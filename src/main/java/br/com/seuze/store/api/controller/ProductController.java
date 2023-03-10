package br.com.seuze.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.com.seuze.store.api.exceptions.ProductNotFountException;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.service.ProductService;
import br.com.seuze.store.api.service.SalesOrderService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("product")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	SalesOrderService salesOrderService; 
	
	@ApiOperation(value = "API to enter a product in stock.")
	@PostMapping("")
	public ResponseEntity<?> registerProduct(@RequestBody Product product) {
			return new ResponseEntity<>(productService.registerProduct(product),
					HttpStatus.CREATED);
		
	}
	
	@ApiOperation("API to list stock products.")
	@GetMapping("")
	public ResponseEntity<?> getAllProducts(){
			return new ResponseEntity<>(productService.listAllProducts(),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a product by sku.")
	@GetMapping("/{sku}")
	public ResponseEntity<?> getProductBySku(@PathVariable String sku){
			return new ResponseEntity<>(productService.searchBySku(sku),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a list products from a brand.")
	@GetMapping("/brand/{brand}")
	public ResponseEntity<?> getProductByBrand(@PathVariable("brand") String brand){
		brand = brand.toUpperCase();
		if(!ProductBrandEnum.getBrandList().contains(brand)) {
			throw new ProductNotFountException("This brand is unknown: " + brand 
					+". Try one of these brands: " + ProductBrandEnum.getBrandList());
		}
		
		return new ResponseEntity<>(
						productService.searchByBrand(ProductBrandEnum.valueOf(brand)),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a list products from a category.")
	@GetMapping("/category/{category}")
	public ResponseEntity<?> getProductByCategory(@PathVariable("category") String category){
		category = category.toUpperCase();
		if(!ProductCategoryEnum.getCategoryList().contains(category)) {
			throw new ProductNotFountException("This category is unknown: " + category 
					+". Try one of these categories: " + ProductCategoryEnum.getCategoryList());
		}
		return new ResponseEntity<>(
						productService.searchByCategory(ProductCategoryEnum.valueOf(category)),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a list products from a department.")
	@GetMapping("/department/{department}")
	public ResponseEntity<?> getProductByDepartment(@PathVariable("department") String department){
		department = department.toUpperCase();
		if(!ProductDepartmentEnum.getDepartmentList().contains(department)) {
			throw new ProductNotFountException("This department is unknown: " + department 
					+". Try one of these departments: " + ProductDepartmentEnum.getDepartmentList());
		}
		return new ResponseEntity<>(
						productService.searchByDepartment(ProductDepartmentEnum.valueOf(department)),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a list products from a type.")
	@GetMapping("/type/{type}")
	public ResponseEntity<?> getProductByType(@PathVariable("type") String type){
		type = type.toUpperCase();
		if(!ProductTypeEnum.getTypeList().contains(type)) {
			throw new ProductNotFountException("This department is unknown: " + type 
					+". Try one of these departments: " + ProductTypeEnum.getTypeList());
		}
		return new ResponseEntity<>(
						productService.searchByType(ProductTypeEnum.valueOf(type)),
						HttpStatus.OK);
		
	}
	
	@ApiOperation("API to find a list products from a color.")
	@GetMapping("/color/{color}")
	public ResponseEntity<?> getProductByColor(@PathVariable("color") String color){
		color = color.toUpperCase();
		if(!ProductColorEnum.getColorList().contains(color)) {
			throw new ProductNotFountException("This color is unknown: " + color 
					+". Try one of these colors: " + ProductColorEnum.getColorList());
		}
		return new ResponseEntity<>(
						productService.searchByColor(ProductColorEnum.valueOf(color.toUpperCase())),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to find a list products from a size.")
	@GetMapping("/size/{size}")
	public ResponseEntity<?> getProductBySize(@PathVariable("size") int size){
		return new ResponseEntity<>(
						productService.searchBySize(size),
						HttpStatus.OK);
	}
	
	@ApiOperation("API to add a product to a sales order.")
	@PostMapping("/add-to-sales-order/{orderId}")
	public ResponseEntity<?> addProductToSalesOrder(@PathVariable Long orderId, 
			@RequestBody ObjectNode objectNode) {
		String sku = objectNode.get("sku").asText();
		Integer amount = Integer.parseInt(objectNode.get("amount").asText());
			return new ResponseEntity<>(productService.addProductToSalesOrder(sku, orderId, amount),
					HttpStatus.OK);
		
	}
	
	@ApiOperation("API to remove a product from a sales order.")
	@PostMapping("/remove-from-sales-order/{orderId}")
	public ResponseEntity<?> removeProductFromSalesOrder(@PathVariable Long orderId, @RequestBody ObjectNode objectNode) {
		String sku = objectNode.get("sku").asText();
		Integer amount = Integer.parseInt(objectNode.get("amount").asText());
		
			return new ResponseEntity<>(productService.removeProductFromSalesOrder(sku, orderId, amount),
					HttpStatus.OK);
	}
}
