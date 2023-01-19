package br.com.seuze.store.api.enumerations;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ProductCategoryEnum {
	WOMEN("W", "Women's"), MEN("M", "Men's"), CHILDREN("C", "Children’s");
	
	private final String value;
    private final String description;
	
    private static final Map<String, ProductCategoryEnum> listOfCategorys = new LinkedHashMap<>();
	 
    static {
    	for(ProductCategoryEnum productCategory : ProductCategoryEnum.values()) { 
    		listOfCategorys.put(productCategory.getValue(), productCategory);
    		}
    }
    
    private ProductCategoryEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public static Map<String, ProductCategoryEnum> getListofcategorys() {
		return listOfCategorys;
	}
}
