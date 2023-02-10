package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ProductTypeEnum {
	SHIRT("SHI"), SHORTS("SCT"), PANTS("PAN"),
	SHOES("SHO"), HAT("HAT"), SWIMMING_CAP("SCA");
	
    private final String value;
	
   
    
    private ProductTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	private static final List<String> listOfTypes = new ArrayList<>();
	 
    static {
    	for(ProductTypeEnum productType : ProductTypeEnum.values()) { 
    		listOfTypes.add(productType.toString());
    		}
    }
    
    public static List<String> getTypeList() {
		return listOfTypes;
	}
}
