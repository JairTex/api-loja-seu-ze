package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum PixKeyTypeEnum {
	DOCUMENT, EMAIL, PHONE_NUMBER;

	private static final List<String> listOfPixKeyType = new ArrayList<>();
	 
    static {
    	for(PixKeyTypeEnum pixKeyType : PixKeyTypeEnum.values()) { 
    		listOfPixKeyType.add(pixKeyType.toString());
    		}
    }
    
    public static List<String> getTypeList() {
		return listOfPixKeyType;
	}
 }
