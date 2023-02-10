package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum ProductColorEnum {
	BLUE("BLU"), GREEN("GRE"), YELLOW("YEL"), 
	RED("RED"), GRAY("GRA"),	BLACK("BLA"), 
	WHITE("WHI"), OTHER("OTH");
	
    private final String value;
    
	private ProductColorEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	private static final List<String> listOfColors = new ArrayList<>();
	 
    static {
    	for(ProductColorEnum productColor : ProductColorEnum.values()) { 
    		listOfColors.add(productColor.toString());
    		}
    }
    
    public static List<String> getColorList() {
		return listOfColors;
	}
}
