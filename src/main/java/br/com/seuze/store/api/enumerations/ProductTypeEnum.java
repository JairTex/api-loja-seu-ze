package br.com.seuze.store.api.enumerations;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum ProductTypeEnum {
	SHIRT("SHI", "Shirt"), SHORTS("SCT", "Shorts"), PANTS("PAN", "Pants"),
	SHOES("SHO", "Shoes"), HAT("HAT", "Hat"), SWIMMING_CAP("SCA", "Swimming Cap");
	
    private final String value;
    private final String description;
	
    private static final Map<String, ProductTypeEnum> listOfTypes = new LinkedHashMap<>();
	 
    static {
    	for(ProductTypeEnum productType : ProductTypeEnum.values()) { 
    		listOfTypes.put(productType.getValue(), productType);
    		}
    }
    
    private ProductTypeEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public static Map<String, ProductTypeEnum> getListoftypes() {
		return listOfTypes;
	}
}
