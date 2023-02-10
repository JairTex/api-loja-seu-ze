package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum ProductCategoryEnum {
	WOMEN("W"), MEN("M"), CHILDREN("C");
	
	private final String value;
  
    private ProductCategoryEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	private static final List<String> listOfCategorys = new ArrayList<>();
	 
    static {
    	for(ProductCategoryEnum productCategory : ProductCategoryEnum.values()) { 
    		listOfCategorys.add(productCategory.toString());
    		}
    }
    
    public static List<String> getCategoryList() {
		return listOfCategorys;
	}
}
