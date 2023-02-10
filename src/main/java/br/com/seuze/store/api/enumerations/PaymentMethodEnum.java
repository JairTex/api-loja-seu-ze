package br.com.seuze.store.api.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum PaymentMethodEnum {
	CASH, PIX, DEBIT_CARD, CREDIT_CARD;
	
	private static final List<String> listOfPaymentMethod = new ArrayList<>();
	 
    static {
    	for(PixKeyTypeEnum paymentMethod : PixKeyTypeEnum.values()) { 
    		listOfPaymentMethod.add(paymentMethod.toString());
    		}
    }
    
    public static List<String> getTypeList() {
		return listOfPaymentMethod;
	}
}
