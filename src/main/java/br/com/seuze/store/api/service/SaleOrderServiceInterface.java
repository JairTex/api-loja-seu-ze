package br.com.seuze.store.api.service;

import java.util.List;
import br.com.seuze.store.api.model.SalesOrder;
import br.com.seuze.store.api.strategies.CardPaymentStrategy;
import br.com.seuze.store.api.strategies.CashPaymentStrategy;
import br.com.seuze.store.api.strategies.PaymentStrategy;

public interface SaleOrderServiceInterface {
	public SalesOrder createSalesOrder();
	
	public SalesOrder searchById(Long id);
	
	public List<SalesOrder> listAllSalesOrder();
	
	public SalesOrder processSalesOrder(Long salesOrderId);
	
	public SalesOrder addPaymentMethodCash(Long salesOrderId, CashPaymentStrategy payment);
	
	public SalesOrder addPaymentMethodCard(Long salesOrderId, CardPaymentStrategy payment);
	
	public SalesOrder registerPaymentMethod(Long salesOrderId, PaymentStrategy payment);
	
	public SalesOrder cancelSalesOrder(Long salesOrderId);
	
	public SalesOrder sell(Long salesOrderId);
	
	public PaymentStrategy completePayment(PaymentStrategy payment, 
			Double total, Long SalesOrderId, Long SaleId);

	public SalesOrder addDocument(Long salesOrderId, String document);
	
	public boolean documentValid(String document);
	
	public boolean documentFormatAccepted(String document);
	
	public boolean documentDigitsAccepted(String document);
	
	public boolean cardDigitsAccepted(String number);
	
	public boolean cardDateExpirationValid(String date);
	
	public boolean cardDateExpirationFormatAccepted(String date);
	
	public boolean cardNumberFormatAccepted(String cardNumber);
	
	public boolean cardSecurityFormatAccepted(String cvv);
	
	public boolean emailValid(String email);
	
	public boolean emailFormatAccepted(String email);
	
	public boolean phoneNumberValid(String phoneNumber);
	
	public boolean phoneNumberFormatAccepted(String phoneNumber);
	
	public boolean validateSalesOrderSearch(Long orderSaleId);
	
	public boolean salesOrderCanBeProcessed(SalesOrder salesOrder);
	
	public boolean salesOrderCanBeSold(SalesOrder salesOrder);
	
	public boolean salesOrderCanBeCanceled(SalesOrder salesOrder);
}
