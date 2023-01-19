package br.com.seuze.store.api.enumerations;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ProductDepartmentEnum {
	CLOTHING("CL", "Clothing"), UNDERWEAR("UW", "Underwear "), 
	FOOTWEAR("FW", "Footwear"), ACCESSORIES("AC", "Accessories");
	
    private final String value;
    private final String description;
    
    private static final Map<String, ProductDepartmentEnum> listOfDepartments = new LinkedHashMap<>();
	 
    static {
    	for(ProductDepartmentEnum productDepartment : ProductDepartmentEnum.values()) { 
    		listOfDepartments.put(productDepartment.getValue(), productDepartment);
    		}
    }
    
    private ProductDepartmentEnum(String value, String description){
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public static Map<String, ProductDepartmentEnum> getListofdepartments() {
		return listOfDepartments;
	}
}
