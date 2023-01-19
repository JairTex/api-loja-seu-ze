package br.com.seuze.store.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SaleService implements SaleServiceInterface{
	/*
	private static SaleData saleData = new SaleData();
	private static ProductService productService = new ProductService();
	
	public Sale registerSale() {
		Sale sale = new Sale(new ArrayList<>(), 0.0, null);
		saleData.save(sale);
		return sale;
	}
	
	public Sale searchSaleById(String id) {
		Sale sale = (Sale) saleData.getItem(id);
		return sale;
	}
	
	public Sale orderSale(String id) {
		Sale sale = (Sale) saleData.getItem(id);
		ArrayList<Product> saleAvailable = new ArrayList<>();
		
		for(Product product : sale.getBag()) {
			Product productInStock = (Product) productService.searchBySku(product.getSku());
			if(productInStock.getAmount() >= product.getAmount()) { 
				productService.removeFromStock(product.getSku(), product.getAmount());
			 	saleAvailable.add(product);
			 	
			}
		}
		sale.setBag(saleAvailable);
		saleData.update(sale);
		
		return sale;
	}
	
	public void sell(String id, PaymentStrategy payment) {
		Sale sale = (Sale) saleData.getItem(id);
		sale.setDate(Calendar.getInstance());
		sale.setTotalValue(calculateTotalValue(sale.getSaleId()));
		calculateTotalValue(sale.getSaleId());
		sale.setPayment(payment);
		sale.setCompleted(true);
		saleData.update(sale);
	}
	
	public Double calculateTotalValue(String id) {
		Sale sale = (Sale) saleData.getItem(id);
		
		double total = 0.;
		for(Product product : sale.getBag()) {
			total += (product.getValue() * product.getAmount()); 
		}
		sale.setTotalValue(total);
		
		saleData.update(sale);
		
		return sale.getTotalValue();
	}
	
	public void cleanBag(String id) {
		Sale sale = (Sale) saleData.getItem(id);
		for(Product product : sale.getBag()) {
			productService.addToStock(product.getSku(), product.getAmount());
		}
		ArrayList<Product> emptyBag = new ArrayList<>();
		sale.setBag(emptyBag);
		saleData.update(sale);
	}
	
	public boolean registerDocument(String id, String document) {
		if(documentFormatAccepted(document)) {
			if(documentDigitsAccepted(document)) {
				Sale sale = (Sale) saleData.getItem(id);
				sale.setDocument(document);
				saleData.update(sale);
				return true;
			}
		}
		return false;
	}
	
	public boolean documentIsValid(String document) {
		if(documentFormatAccepted(document)) {
			if(documentDigitsAccepted(document)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean documentFormatAccepted(String document) {
		String documentRegex = "^\\b([0-9]{3})\\.([0-9]{3})\\.([0-9]{3})\\-([0-9]{2})$";
		Pattern documentPattern = Pattern.compile(documentRegex);
		
		Matcher match = documentPattern.matcher(document);
	
		return match.find();
	}
	
	public boolean documentDigitsAccepted(String document) {
		document = document.replace(".", "");
		document = document.replace("-", "");
		
		String[] documentStringArray = document.split("");
		ArrayList<String> documentStringList = new ArrayList<>();
		
		int counter = 10;
		int total = 0;
		for(int i = 0; i < 9;i++) {
			documentStringList.add(documentStringArray[i]);
			
			total += (Integer.parseInt(documentStringArray[i]) * counter);
			counter--;
		}
		total %= 11;
		
		Integer digit;
		if(total < 2) {
			digit = 0;
		} else {
			digit = 11 - total;
		}
		
		documentStringList.add(digit.toString());
		counter = 11;
		total = 0;
		for(String numero : documentStringList) {
			total += (Integer.parseInt(numero) * counter);
			counter--;
		}
		
		total %= 11;
		if(total < 2) {
			digit = 0;
		} else {
			digit = 11 - total;
		}
		documentStringList.add(digit.toString());
		
		String cpdValid = "";
		for(String numero : documentStringList) {
			cpdValid += numero;
		}
		
		return document.equalsIgnoreCase(cpdValid);
	}
	
	public LinkedHashMap<String, Object> finishedSales(){
		LinkedHashMap<String, Object> allSales = saleData.listAll();
		LinkedHashMap<String, Object> finishedSales = new LinkedHashMap<>();
		
		Integer count = 1;
		for(String key : allSales.keySet()) {
			Sale sale = (Sale) allSales.get(key);
			if(sale.isCompleted()) { 
				finishedSales.put(count.toString(), sale);
				count++;
			}
		}
		
		return finishedSales;
	}
	
	public SaleData listSales() {
		return saleData;
	}*/
}
