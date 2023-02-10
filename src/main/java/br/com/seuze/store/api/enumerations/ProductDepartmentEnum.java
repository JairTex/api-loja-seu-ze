package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ProductDepartmentEnum {
	CLOTHING("CL"), UNDERWEAR("UW"), 
	FOOTWEAR("FW"), ACCESSORIES("AC");
	
    private final String value;
    
    
    
    private ProductDepartmentEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
    private static final List<String> listOfDepartments = new ArrayList<>();
	 
    static {
    	for(ProductDepartmentEnum productDepartment : ProductDepartmentEnum.values()) { 
    		listOfDepartments.add(productDepartment.toString());
    		}
    }
    
    public static List<String> getDepartmentList() {
		return listOfDepartments;
	}
}
