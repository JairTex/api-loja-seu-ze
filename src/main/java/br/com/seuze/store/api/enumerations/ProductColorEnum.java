package br.com.seuze.store.api.enumerations;

import java.util.LinkedHashMap;
import java.util.Map;

public enum ProductColorEnum {
	BLUE("BLU", "Blue"), GREEN("GRE", "Green"), YELLOW("YEL", "Yellow"), 
	RED("RED", "Red"), GRAY("GRA", "Gray"),	BLACK("BLA", "Black"), 
	WHITE("WHI", "White"), OTHER("OTH", "Other");
	
    private final String value;
    private final String description;
    
    private static final Map<String, ProductColorEnum> listOfColors = new LinkedHashMap<>();
	 
    static {
    	for(ProductColorEnum productColor : ProductColorEnum.values()) { 
    		listOfColors.put(productColor.getValue(), productColor);
    		}
    }
    
	private ProductColorEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}
	
	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
	public static Map<String, ProductColorEnum> getListofcolors() {
		return listOfColors;
	}
}
