package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum ProductBrandEnum {
	MIXED("MIX"), CRIS_BARROS("CBA"), NK_STORE("NKS"),
	NIKE("NIK"), ADIDAS("ADI"), LACOSTE("LAC"),
	BURBERRY("BUR"), LEVIS("LEV"), OAKLEY("OAK");
	
	private final String value;
	
    private ProductBrandEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	private static final List<String> listOfBrands = new ArrayList<>();
	 
    static {
    	for(ProductBrandEnum productBrand : ProductBrandEnum.values()) { 
    		listOfBrands.add(productBrand.toString());
    		}
    }
    
    public static List<String> getBrandList() {
		return listOfBrands;
	}
}
