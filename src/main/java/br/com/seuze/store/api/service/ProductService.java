package br.com.seuze.store.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.model.ProductSold;
import br.com.seuze.store.api.model.SalesOrder;
import br.com.seuze.store.api.repository.ProductRepository;
import br.com.seuze.store.api.repository.ProductSoldRepository;
import br.com.seuze.store.api.repository.SalesOrderRepository;

@Service
public class ProductService{
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SalesOrderRepository salesOrderRepository;
	@Autowired
	ProductSoldRepository productSoldRepository;
	
	public Product register(Product product) {
		product.setSku(generateSku(product));
		
		if(productRepository.existsById(product.getSku())) {
			Product productRegistered = productRepository.findById(product.getSku()).get();
			
			product.setAmount(productRegistered.getAmount() + product.getAmount());
			
			if(isValid(product)) {
				productRepository.save(product);
			} else {
				throw new RuntimeException("Produto inválido!");
			}
		} else {
			if(isValid(product)) {
				productRepository.save(product);
			} else {
				throw new RuntimeException("Produto inválido!");
			}
		}
		return product;
	}
	
	private static String generateSku(Product product) {
		return product.getBrand().getValue() + "-" + product.getCategory().getValue() 
				+ product.getDepartment().getValue() + "-" + product.getType().getValue() + "-" 
				+ product.getColor().getValue() + "-" + product.getSize();
	}
	
	public boolean isValid(Product product) {
		if(product.getAmount() >= 0 && product.getUnitprice() >= 0 && product.getSize() > 0){	
			return true;
		}
		return false;
	}
	
	public List<Product> listAllProducts() {
		return productRepository.findAll();
	}
	
	public Product searchBySku(String sku) {
		return productRepository.findById(sku).get();
	}
	
	public List<Product> searchByBrand(ProductBrandEnum brand) {
		return productRepository.searchByBrand(brand);
	}
	
	public List<Product> searchByCategory(ProductCategoryEnum category) {
		return productRepository.searchByCategory(category);
	}
	
	public List<Product> searchByDepartment(ProductDepartmentEnum department){
		return productRepository.searchByDepartment(department);
	}
	
	public List<Product> searchByType(ProductTypeEnum type) {
		return productRepository.searchByType(type);
	}
	
	public List<Product> searchByColor(ProductColorEnum color) {
		return productRepository.searchByColor(color);
	}
	
	public List<Product> searchBySize(int size) {
		return productRepository.searchBySize(size);
	}
	
	public SalesOrder addProductToBag(String sku, Long orderId, int amount) {
		SalesOrder salesOrder = salesOrderRepository.findById(orderId).get();
		Product product = productRepository.findById(sku).get();
		
		ArrayList<String> skuSoldList = new ArrayList<String>();
		for(ProductSold ps : salesOrder.getBag()) {
			skuSoldList.add(ps.getSku());
		}
		
		if(salesOrder.isCompleted()) {
			throw new RuntimeException("Ordem de Compra está finaliada!");
		} else {
			if(skuSoldList.contains(sku)) {
				for(ProductSold ps : salesOrder.getBag()) {
					if(ps.getSku().equals(sku)) {
						ps.getId();
						ps.setAmount(ps.getAmount() + amount);
						ps.setUnitprice(product.getUnitprice());
						ps.setPrice(ps.getUnitprice() * ps.getAmount());
						
						productSoldRepository.save(ps);
					}
				}
			} else {
				ProductSold ps = new ProductSold();
				ps.setSku(sku);
				ps.setOrderId(orderId);				
				ps.setAmount(amount);
				ps.setUnitprice(product.getUnitprice());
				ps.setPrice(ps.getUnitprice() * ps.getAmount());
				
				productSoldRepository.save(ps);
				
				salesOrder.getBag().add(ps);
			}
		} 
		
		salesOrderRepository.save(salesOrder);
		
		return salesOrder;
	}
	
	
	/*
	public boolean removeFromStock(String sku, int amount) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		if(stock.containsKey(sku.toUpperCase())) {
			Product product = (Product) stock.get(sku);
			product.setAmount(product.getAmount() - amount);
			
			productData.update(product);
			return true;
		}		
		return false;
	}
	
	public boolean addToStock(String sku, int amount) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		if(stock.containsKey(sku.toUpperCase())) {
			Product product = (Product) stock.get(sku);
			product.setAmount(product.getAmount() + amount);
			
			productData.update(product);
			return true;
		}		
		return false;
	}
	
	public boolean addProductToBag(String sku, String bagId, int amount) throws CloneNotSupportedException {
		LinkedHashMap<String, Object> salesList = saleData.listAll();
		LinkedHashMap<String, Object> stock = productData.listAll();
		if(salesList.containsKey(bagId) && stock.containsKey(sku)) {
			Product productBag = (Product) stock.get(sku);
			Product productBagClone = (Product) productBag.clone();
			productBagClone.setAmount(amount);
			
			Sale sale = (Sale) salesList.get(bagId);
			sale.getBag().add(productBagClone);
			saleData.update(sale);
			
			return true;
		}
		return false;
	}
	
	public Object searchBySku(String sku) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		
		for(String key : stock.keySet()) {
			if (key.equalsIgnoreCase(sku)) {
				Product product = (Product) stock.get(key);
				return product;
			}
		}
		return null;
	}
	
	public LinkedHashMap<String, Object> searchByCategory(ProductCategory category) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		LinkedHashMap<String, Object> stockCategory = new LinkedHashMap<>();
		
		for(String key : stock.keySet()) {
			Product product = (Product) stock.get(key);
			if (product.getCategory() == category) {
				stockCategory.put(product.getSku(), product);
			}
		}
		return stockCategory;
	}
	
	public LinkedHashMap<String, Object> searchByDepartment(ProductDepartment department) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		LinkedHashMap<String, Object> stockDepartment = new LinkedHashMap<>();
		
		for(String key : stock.keySet()) {
			Product product = (Product) stock.get(key);
			if (product.getDepartment() == department) {
				stockDepartment.put(product.getSku(), product);
			}
		}
		return stockDepartment;
	}
	
	public LinkedHashMap<String, Object> searchByType(ProductType type) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		LinkedHashMap<String, Object> stockType = new LinkedHashMap<>();
		
		for(String key : stock.keySet()) {
			Product product = (Product) stock.get(key);
			if (product.getType() == type) {
				stockType.put(product.getSku(), product);
			}
		}
		return stockType;
	}
	
	public LinkedHashMap<String, Object> searchByColor(ProductColor color) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		LinkedHashMap<String, Object> stockColor = new LinkedHashMap<>();
		
		for(String key : stock.keySet()) {
			Product product = (Product) stock.get(key);
			if (product.getColor() == color) {
				stockColor.put(product.getSku(), product);
			}
		}
		return stockColor;
	}
	
	public LinkedHashMap<String, Object> searchBySize(String size) {
		LinkedHashMap<String, Object> stock = productData.listAll();
		LinkedHashMap<String, Object> stockSize = new LinkedHashMap<>();
		
		for(String key : stock.keySet()) {
			Product product = (Product) stock.get(key);
			if (product.getSize() == size) {
				stockSize.put(product.getSku(), product);
			}
		}
		return stockSize;
	} public ProductData listStock() { return productData; }
	 */
}
