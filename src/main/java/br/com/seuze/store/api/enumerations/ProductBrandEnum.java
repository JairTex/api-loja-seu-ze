package br.com.seuze.store.api.enumerations;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ProductBrandEnum {
	MIXED("MIX", "Mixed"), CRIS_BARROS("CBA", "Cris Barros"), NK_STORE("NKS", "NK Store"),
	NIKE("NIK", "Nike"), ADIDAS("ADI", "Adidas"), LACOSTE("LAC", "Lacoste"),
	BURBERRY("BUR", "Burberry"), LEVIS("LEV", "Levi's"), OAKLEY("OAK", "Oakley");
	
	private final String value;
    private final String description;
	
    private static final Map<String, ProductBrandEnum> listOfBrands = new LinkedHashMap<>();
	 
    static {
    	for(ProductBrandEnum productBrand : ProductBrandEnum.values()) { 
    		listOfBrands.put(productBrand.getValue(), productBrand);
    		}
    }
    
    private ProductBrandEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public static Map<String, ProductBrandEnum> getListOfBrands() {
		return listOfBrands;
	}
}
