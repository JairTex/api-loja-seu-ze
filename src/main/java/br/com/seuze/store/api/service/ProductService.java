package br.com.seuze.store.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.enumerations.SalesOrderStatusEnum;
import br.com.seuze.store.api.exceptions.InvalidProductException;
import br.com.seuze.store.api.exceptions.ProductNotFountException;
import br.com.seuze.store.api.exceptions.SalesOrderNotFound;
import br.com.seuze.store.api.exceptions.SalesOrderProcessingException;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.model.ProductSalesOrder;
import br.com.seuze.store.api.model.SalesOrder;
import br.com.seuze.store.api.repository.ProductRepository;
import br.com.seuze.store.api.repository.ProductSalesOrderRepository;
import br.com.seuze.store.api.repository.SalesOrderRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService implements ProductServiceInterface{
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SalesOrderRepository salesOrderRepository;
	@Autowired
	ProductSalesOrderRepository productSalesOrderRepository;
	
	public Product registerProduct(Product product) {
		if(product.getSku()==null) {
			product.setSku(generateSku(product));
		}
		
		if(productRepository.findBySku(product.getSku()).isEmpty()){
			if(validateProduct(product)) {
				productRepository.save(product);
			} else {
				System.err.println("Invalid Product was not registered");
				throw new InvalidProductException("Invalid Product, verify amount, unit price and size of this product!");
			}
		} else {
			if(validateProduct(product)) {
				Product productInStock = productRepository.findBySku(product.getSku()).get(0);
				product.setId(productInStock.getId());
				product.setAmount(productInStock.getAmount() + product.getAmount());
				
				productRepository.save(product);
			} else {
				log.info("Invalid Product was not updated");
				throw new InvalidProductException("Invalid Product, verify amount, unit price and size of this product!");
			}
		}	
		return product;
	}
	
	public String generateSku(Product product) {
		return product.getBrand().getValue() + "-" + product.getCategory().getValue() 
				+ product.getDepartment().getValue() + "-" + product.getType().getValue() + "-" 
				+ product.getColor().getValue() + "-" + product.getSize();
	}
	
	public boolean validateProduct(Product product) {
		if(product.getAmount() >= 0 && product.getUnitprice() >= 0 && product.getSize() > 0){	
			return true;
		}
		return false;
	}
	
	public List<Product> listAllProducts() {
		if(!productRepository.findAll().isEmpty()) {
			return productRepository.findAll();
		}else {
			log.info("Product searched by client not found!");
			throw new ProductNotFountException("There are no registered products!");
		}
	}
	
	public List<Product> searchBySku(String sku) {
		if(!productRepository.findBySku(sku).isEmpty()) {
			return productRepository.findBySku(sku);
		}else {
			log.info("Product searched by client for the sku not found!");
			throw new ProductNotFountException("This product does not exist!");
		}
	}
	
	public List<Product> searchByBrand(ProductBrandEnum brand) {
		if(!productRepository.findByBrand(brand).isEmpty()) {
			return  productRepository.findByBrand(brand);
		}else {
			log.info("Product searched by client for the brand not found!");
			throw new ProductNotFountException("There are no registered products of this brand!");
		}
	}
	
	public List<Product> searchByCategory(ProductCategoryEnum category) {
		if(!productRepository.findByCategory(category).isEmpty()) {
			return  productRepository.findByCategory(category);
		}else {
			log.info("Product searched by client for the category not found!");
			throw new ProductNotFountException("There are no registered products of this category!");
		}
	}
	
	public List<Product> searchByDepartment(ProductDepartmentEnum department){
		if(!productRepository.findByDepartment(department).isEmpty()) {
			return  productRepository.findByDepartment(department);
		}else {
			log.info("Product searched by client for the department not found!");
			throw new ProductNotFountException("There are no registered products of this department!");
		}
	}
	
	public List<Product> searchByType(ProductTypeEnum type) {
		if(!productRepository.findByType(type).isEmpty()) {
			return productRepository.findByType(type);
		}else {
			log.info("Product searched by client for the type not found!");
			throw new ProductNotFountException("There are no registered products of this type!");
		}
	}
	
	public List<Product> searchByColor(ProductColorEnum color) {
		if(!productRepository.findByColor(color).isEmpty()) {
			return productRepository.findByColor(color);
		}else {
			log.info("Product searched by client for the color not found!");
			throw new ProductNotFountException("There are no registered products of this color!");
		}
	}
	
	public List<Product> searchBySize(int size) {
		if(!productRepository.findBySize(size).isEmpty()) {
			return productRepository.findBySize(size);
		}else {
			log.info("Product searched by client for the size not found!");
			throw new ProductNotFountException("There are no registered products of this color!");
		}
	}
	
	public SalesOrder addProductToSalesOrder(String sku, Long orderId, int amount) {
		validateSearch(orderId, sku);
		
		SalesOrder salesOrder = salesOrderRepository.findById(orderId).get();
		Product product = productRepository.findBySku(sku).get(0);
		
		ArrayList<String> skuSoldList = new ArrayList<String>();
		for(ProductSalesOrder ps : salesOrder.getBag()) {
			skuSoldList.add(ps.getSku());
		}
		
		ProductCanBeAdded(orderId, sku);
			
		if(skuSoldList.contains(sku)) {
			for(ProductSalesOrder ps : salesOrder.getBag()) {
				if(ps.getSku().equals(sku)) {
					ps.getId();
					ps.setAmount(ps.getAmount() + amount);
					ps.setUnitprice(product.getUnitprice());
					ps.setPrice(ps.getUnitprice() * ps.getAmount());
					
					productSalesOrderRepository.save(ps);
				}
			}
		} else {
			ProductSalesOrder ps = new ProductSalesOrder();
			ps.setSku(sku);
			ps.setOrderId(orderId);				
			ps.setAmount(amount);
			ps.setUnitprice(product.getUnitprice());
			ps.setPrice(ps.getUnitprice() * ps.getAmount());
			
			productSalesOrderRepository.save(ps);
			salesOrder.getBag().add(ps);
		}
	
		calculateTotalSalesOrder(salesOrder);
		
		salesOrderRepository.save(salesOrder);
		
		return salesOrder;
	}
	
	public boolean ProductCanBeAdded(Long salesOrderSaleId, String sku) {
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderSaleId).get();
		
		if(salesOrder.getStatus().equals(SalesOrderStatusEnum.FINISHED)) {
			log.info("Client tried add a product in a sales order already finished.");
			throw new SalesOrderProcessingException("This sales order was finished!");
		}
		if(salesOrder.getStatus().equals(SalesOrderStatusEnum.CANCELED)) {
			log.info("Client tried add a product in a sales order already canceled.");
			throw new SalesOrderProcessingException("This sales order was canceled!");
		}
		if(salesOrder.getStatus().equals(SalesOrderStatusEnum.PROCESSED)) {
			salesOrder.setStatus(SalesOrderStatusEnum.OPEN);
		}
		salesOrderRepository.save(salesOrder);
		return true;
	}
	
	public void removeProduct(String sku, Long orderId, int amount) {
		validateSearch(orderId, sku);
		
		SalesOrder salesOrder = salesOrderRepository.findById(orderId).get();
		
		List<ProductSalesOrder> psoList = salesOrder.getBag().stream()
				.filter(pso -> pso.getSku().equalsIgnoreCase(sku))
				.collect(Collectors.toList());
		
		for(ProductSalesOrder pso : psoList) {
			if(pso.getAmount() > amount) {
				pso.setAmount(pso.getAmount() - amount);
				pso.setPrice(pso.getUnitprice() * pso.getAmount());
				productSalesOrderRepository.save(pso);
			} else {
				salesOrder.getBag().remove(psoList.get(0));
			}
		}	
		
		calculateTotalSalesOrder(salesOrder);
	}
	
	public SalesOrder removeProductFromSalesOrder(String sku, Long orderId, int amount) {
		validateSearch(orderId, sku);
		productCanBeRemoved(orderId, sku);
		
		removeProduct(sku, orderId, amount);
		
		SalesOrder salesOrder = salesOrderRepository.findById(orderId).get();
		
		return salesOrderRepository.save(salesOrder);
	}
	
	public boolean validateSearch(Long orderSaleId, String sku) {
		if(salesOrderRepository.findById(orderSaleId).isEmpty()) {
			log.info("Sales Order searched by client not found!");
			throw new SalesOrderNotFound("This sales order does not exist!");
		}
		if(productRepository.findBySku(sku).isEmpty()) {
			log.info("Product searched by client not found!");
			throw new ProductNotFountException("This product does not exist!");
		}
		return true;
	}
	
	public boolean productCanBeRemoved(Long orderSaleId, String sku) {
		List<String> skuList = new ArrayList<>();
		for(ProductSalesOrder pso : salesOrderRepository.findById(orderSaleId).get().getBag()) {
			skuList.add(pso.getSku());
		}
		if(!skuList.contains(sku)) {
			log.info("Product searched by client not found in Sales Order!");
			throw new SalesOrderNotFound("This Product does not exist in this Sales Order!");
		}
		return true;
	}
	
	public Double calculateTotalSalesOrder(SalesOrder salesOrder) {
		int total = 0;
		for (ProductSalesOrder pso : salesOrder.getBag()) {
			total += pso.getPrice();
		}
		salesOrder.setTotal(total);
		salesOrderRepository.save(salesOrder);
		
		return salesOrder.getTotal(); 
	}
}
