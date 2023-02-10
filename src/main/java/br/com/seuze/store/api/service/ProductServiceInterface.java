package br.com.seuze.store.api.service;

import java.util.List;
import br.com.seuze.store.api.enumerations.ProductBrandEnum;
import br.com.seuze.store.api.enumerations.ProductCategoryEnum;
import br.com.seuze.store.api.enumerations.ProductColorEnum;
import br.com.seuze.store.api.enumerations.ProductDepartmentEnum;
import br.com.seuze.store.api.enumerations.ProductTypeEnum;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.model.SalesOrder;

public interface ProductServiceInterface {
	public Product registerProduct(Product product);
	
	public String generateSku(Product product);
	
	public boolean validateProduct(Product product);
	
	public List<Product> listAllProducts();
	
	public Product searchBySku(String sku);
	
	public List<Product> searchByBrand(ProductBrandEnum brand);
	
	public List<Product> searchByCategory(ProductCategoryEnum category);
	
	public List<Product> searchByDepartment(ProductDepartmentEnum department);
	
	public List<Product> searchByType(ProductTypeEnum type);
	
	public List<Product> searchByColor(ProductColorEnum color);
	
	public List<Product> searchBySize(int size);
	
	public SalesOrder addProductToSalesOrder(String sku, Long orderId, int amount);
	
	public boolean ProductCanBeAdded(Long salesOrderSaleId, String sku);
	
	public void removeProduct(String sku, Long orderId, int amount);
	
	public SalesOrder removeProductFromSalesOrder(String sku, Long orderId, int amount);
	
	public boolean productCanBeRemoved(Long orderSaleId, String sku);
	
	public boolean validateSearch(Long orderSaleId, String sku);
	
	public Double calculateTotalSalesOrder(SalesOrder salesOrder);
	
}
