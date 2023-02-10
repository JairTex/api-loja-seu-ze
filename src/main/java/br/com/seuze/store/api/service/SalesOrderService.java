package br.com.seuze.store.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seuze.store.api.enumerations.PixKeyTypeEnum;
import br.com.seuze.store.api.enumerations.SalesOrderStatusEnum;
import br.com.seuze.store.api.exceptions.CardDateException;
import br.com.seuze.store.api.exceptions.CardNumberException;
import br.com.seuze.store.api.exceptions.CardSecurityException;
import br.com.seuze.store.api.exceptions.InvalidDocumentException;
import br.com.seuze.store.api.exceptions.InvalidEmailException;
import br.com.seuze.store.api.exceptions.InvalidSizeException;
import br.com.seuze.store.api.exceptions.PhoneNumberException;
import br.com.seuze.store.api.exceptions.PixPaymentException;
import br.com.seuze.store.api.exceptions.SalesOrderCancellationException;
import br.com.seuze.store.api.exceptions.SalesOrderNotFoundException;
import br.com.seuze.store.api.exceptions.SalesOrderProcessingException;
import br.com.seuze.store.api.model.Product;
import br.com.seuze.store.api.model.ProductSalesOrder;
import br.com.seuze.store.api.model.Sale;
import br.com.seuze.store.api.model.SalesOrder;
import br.com.seuze.store.api.repository.ProductRepository;
import br.com.seuze.store.api.repository.ProductSalesOrderRepository;
import br.com.seuze.store.api.repository.SaleRepository;
import br.com.seuze.store.api.repository.SalesOrderRepository;
import br.com.seuze.store.api.strategies.CardPaymentStrategy;
import br.com.seuze.store.api.strategies.CashPaymentStrategy;
import br.com.seuze.store.api.strategies.PaymentStrategy;
import br.com.seuze.store.api.strategies.PixPaymentStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SalesOrderService implements SaleOrderServiceInterface {
	@Autowired
	SalesOrderRepository salesOrderRepository;
	@Autowired
	SaleRepository saleRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductSalesOrderRepository productSalesOrderRepository;
	@Autowired
	ProductService productService;
	
	public SalesOrder createSalesOrder() {
		return salesOrderRepository.save(new SalesOrder());
	}
	
	public SalesOrder searchById(Long id) {
		if(!salesOrderRepository.findById(id).isEmpty()) {
			return salesOrderRepository.findById(id).get();
		}else {
			log.info("Sales Order searched by client not found!");
			throw new SalesOrderNotFoundException("This Sales Order does not exist!");
		}
	}
	
	public List<SalesOrder> listAllSalesOrder() {
		if(!salesOrderRepository.findAll().isEmpty()) {
			return salesOrderRepository.findAll();
		}else {
			log.info("Sales Order searched by client not found!");
			throw new SalesOrderNotFoundException("There are no registered Sales Order!");
		}
	}
	
	public SalesOrder processSalesOrder(Long salesOrderId) {
		validateSalesOrderSearch(salesOrderId);
		
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).get();
		
		salesOrderCanBeProcessed(salesOrder);
		
		List<Product> productInStock = new ArrayList<>();
		for(ProductSalesOrder pso : salesOrder.getBag()) {
			productInStock.add(productService.searchBySku(pso.getSku()));
		}
		
		for(int i = 0; i < salesOrder.getBag().size();i++) {
			if(salesOrder.getBag().get(i).getAmount() > productInStock.get(i).getAmount()) {
				int excess = salesOrder.getBag().get(i).getAmount() - productInStock.get(i).getAmount();
				productService.removeProduct( salesOrder.getBag().get(i).getSku(), 
						salesOrder.getId(), excess);
				i--;
			}
			if(salesOrder.getBag().size()==0){
				break;
			}
		}
		
		salesOrderCanBeProcessed(salesOrder);
		
		salesOrder.setStatus(SalesOrderStatusEnum.PROCESSED);
		
		return salesOrderRepository.save(salesOrder);
	}
	
	public SalesOrder addPaymentMethodCash(Long salesOrderId, CashPaymentStrategy payment) {
		validateSalesOrderSearch(salesOrderId);
		return registerPaymentMethod(salesOrderId, payment);
	}
	
	public SalesOrder addPaymentMethodPix(Long salesOrderId, PixPaymentStrategy payment) {
		validateSalesOrderSearch(salesOrderId);
		
		if(payment.getPixKeyType()==PixKeyTypeEnum.DOCUMENT) {			
			documentValid(payment.getKey());
		}else if(payment.getPixKeyType()==PixKeyTypeEnum.EMAIL) {
			emailValid(payment.getKey());
		}else if(payment.getPixKeyType()==PixKeyTypeEnum.PHONE_NUMBER) {
			phoneNumberValid(payment.getKey());
		}else {
			log.info("Client tried to use a type of pix key that does not exist.");
			throw new PixPaymentException("This pix key type does not exist!");
		}
		
		return registerPaymentMethod(salesOrderId, payment);
	}
	
	public SalesOrder addPaymentMethodCard(Long salesOrderId, CardPaymentStrategy payment) {
		validateSalesOrderSearch(salesOrderId);
		cardNumberFormatAccepted(payment.getCardNumber());
		cardDigitsAccepted(payment.getCardNumber());
		cardSecurityFormatAccepted(payment.getCardSecurity());
		cardDateExpirationValid(payment.getExpirationDate());
		
		return registerPaymentMethod(salesOrderId, payment);
	}
	
	public SalesOrder registerPaymentMethod(Long salesOrderId, PaymentStrategy payment) {
		validateSalesOrderSearch(salesOrderId);
		
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).get();
		salesOrderCanBeProcessed(salesOrder);
		
		processSalesOrder(salesOrderId);
		salesOrder.setPayment(payment);
		salesOrderRepository.save(salesOrder);
		
		return salesOrder;
	}
	
	public SalesOrder cancelSalesOrder(Long salesOrderId) {
		validateSalesOrderSearch(salesOrderId);
		
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).get();
		salesOrderCanBeCanceled(salesOrder);
		
		salesOrder.setStatus(SalesOrderStatusEnum.CANCELED);
		salesOrder.setPayment(null);
		salesOrderRepository.save(salesOrder);
			
		return salesOrder;
	}
	
	public SalesOrder sell(Long salesOrderId) {
		validateSalesOrderSearch(salesOrderId);
		
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).get();
		salesOrderCanBeSold(salesOrder);
		
		salesOrder.setStatus(SalesOrderStatusEnum.FINISHED);
		salesOrderRepository.save(salesOrder);
		
		Sale sale = new Sale();
		sale.setSalesOrder(salesOrder);
		sale = saleRepository.save(sale);				
		salesOrder.setSale_id(sale.getId());
		
		for(ProductSalesOrder pso : salesOrder.getBag()) {
			Product productinStock = productService.searchBySku(pso.getSku());
			productinStock.setAmount(productinStock.getAmount() - pso.getAmount());
			pso.setSaleId(salesOrder.getSale_id());
			pso.setSold(true);	
			productRepository.save(productinStock);
		}
		
		salesOrder.setPayment(completePayment(salesOrder.getPayment(),
				salesOrder.getTotal(), salesOrder.getId(), salesOrder.getSale_id()));
		
		return salesOrderRepository.save(salesOrder);
	}
	
	public PaymentStrategy completePayment(PaymentStrategy payment, Double total, 
			Long SalesOrderId, Long SaleId) {
		payment.setOrderId(SalesOrderId);
		payment.setSaleId(SaleId);
		payment.setTotal(total);
		return payment;
	}

	public SalesOrder addDocument(Long salesOrderId, String document) {
		validateSalesOrderSearch(salesOrderId);
		
		SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).get();
	
		salesOrderCanBeProcessed(salesOrder);
		documentValid(document);
		salesOrder.setDocument(document);
		return salesOrderRepository.save(salesOrder); 
	}
	
	public boolean documentValid(String document) {
		documentFormatAccepted(document);
		documentDigitsAccepted(document);
		return true;
	}
	
	public boolean documentFormatAccepted(String document) {
		String documentRegex = "^\\b([0-9]{3})\\.([0-9]{3})\\.([0-9]{3})\\-([0-9]{2})$";
		Pattern documentPattern = Pattern.compile(documentRegex);
	
		if(documentPattern.matcher(document).matches()){
			return true;
		} else {
			log.info("Invalid client document!");
			throw new InvalidDocumentException("Invalid document format. Accepted format: XXX.XXX.XXX-XX");
		}
	}
	
	public boolean documentDigitsAccepted(String document) {
		document = document.replace(".", "").replace("-", "");
		
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
		
		List<String> invalidDocuments = Arrays.asList("00000000000", "11111111111",
				"22222222222", "33333333333", "44444444444", "55555555555",
				"66666666666", "77777777777", "88888888888", "99999999999");
		
		if(document.equalsIgnoreCase(cpdValid) && !invalidDocuments.contains(document)) {
			return true;
		}else {
			log.info("Invalid client document digits!");
			throw new InvalidDocumentException("Invalid document digits.");
		}
	}
	
	public boolean cardDigitsAccepted(String number) {
		number = number.replaceAll(" ", "");
		int a = 0, b = 0;
		String reverse = new StringBuffer(number).reverse().toString();
		for (int i = 0 ;i < reverse.length();i++) {
			int digit = Character.digit(reverse.charAt(i), 10);
			if(i % 2 == 0) { a += digit; }
			else {
				b += 2 * digit;
				if (digit >= 5) { 
					b -= 9; 
				}
			}
		}
		
		if((a + b) % 10 == 0) {
			return true;
		} else {
			log.info("Invalid client card number digits!");
			throw new CardNumberException("Invalid card number digits format.");
		}
	}
	
	public boolean cardDateExpirationValid(String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yy");
		simpleDateFormat.setLenient(false);
		Date expiry;
		try {
			expiry = simpleDateFormat.parse(date);
		} catch (ParseException e) {
			log.info("Invalid client card format!");
			throw new CardDateException("Invalid card date format. Accepted format: MM/yy");
		} 
		if(expiry.before(new Date())) {
			log.info("Invalid client card expired!");
			throw new CardDateException("This card is expired");
		}
		return true;
	}	
	
	public boolean cardDateExpirationFormatAccepted(String date) {
		String dateRegex = "(^[0-9]{2}/[0-9]{2})$";
		Pattern datePattern = Pattern.compile(dateRegex);
		
		if(datePattern.matcher(date).matches()){
			return true;
		} else {
			log.info("Invalid client card number!");
			throw new CardDateException("Invalid card date format. Accepted format: MM/yy");
		}
	}
	
	public boolean cardNumberFormatAccepted(String cardNumber) {
		String cardNumberRegex = "(^[3-6][0-9]{3}[ ][0-9]{4}[ ][0-9]{4}[ ][0-9]{4})$";
		Pattern cardNumberPattern = Pattern.compile(cardNumberRegex);
		
		if(cardNumberPattern.matcher(cardNumber).matches()){
			return true;
		} else {
			log.info("Invalid client card number!");
			throw new CardNumberException("Invalid card security format. Accepted format: XXXX XXXX XXXX XXXX");
		}
	}
	
	public boolean cardSecurityFormatAccepted(String cvv) {
		String cvvRegex = "(^[0-9]{3})$";
		Pattern cvvPattern = Pattern.compile(cvvRegex);
	
		if(cvvPattern.matcher(cvv).matches()){
			return true;
		} else {
			log.info("Invalid client card security!");
			throw new CardSecurityException("Invalid card security format. Accepted format: XXX");
		}
	}
	
	public boolean emailValid(String email) {
		if(emailFormatAccepted(email)) {
			return true;
		} else {
			log.info("Invalid client email!");
			throw new InvalidEmailException("Invalid email format.");
		}
	}
	
	public boolean emailFormatAccepted(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern emailPattern = Pattern.compile(emailRegex);
	
		return emailPattern.matcher(email).matches();
	}
	
	public boolean phoneNumberValid(String phoneNumber) {
		if(phoneNumberFormatAccepted(phoneNumber)) {
			return true;
		} else {
			log.info("Invalid client phone number!");
			throw new PhoneNumberException("Invalid document format. Accepted format: XX XXXXX-XXXX");
		}
	}
	
	public boolean phoneNumberFormatAccepted(String phoneNumber) {
		String phoneRegex = "(^[0-9]{2}[ ][0-9]{5}\\-[0-9]{4})$|^([0-9]{2}[ ][0-9]{4}\\-[0-9]{4})$";
		Pattern phonePattern = Pattern.compile(phoneRegex);
	
		return phonePattern.matcher(phoneNumber).matches();
	}
	
	public boolean validateSalesOrderSearch(Long orderSaleId) {
		if(salesOrderRepository.findById(orderSaleId).isEmpty()) {
			log.info("Sales Order searched by client not found!");
			throw new SalesOrderNotFoundException("This sales order does not exist!");
		}
		return true;
	}
	
	public boolean salesOrderCanBeProcessed(SalesOrder salesOrder) {
		if(salesOrder.getStatus() == SalesOrderStatusEnum.CANCELED) {
			log.info("Client tried process with a sales order already canceled.");
			throw new SalesOrderProcessingException("This sales order was canceled!");
		}
		if(salesOrder.getStatus() == SalesOrderStatusEnum.FINISHED) {
			log.info("Client tried process with a sales order already finshed.");
			throw new SalesOrderProcessingException("This sales order was finished!");
		}
		if(salesOrder.getBag().isEmpty()) {
			log.info("Client tried empty with a empty bag.");
			throw new SalesOrderProcessingException("This sales order is empty!");
		}
		return true;
	}
	
	public boolean salesOrderCanBeSold(SalesOrder salesOrder) {
		if(salesOrder.getStatus() == SalesOrderStatusEnum.OPEN) {
			log.info("Client tried sale with a sales order still open.");
			throw new SalesOrderProcessingException("This sales order is still open, try processing it!");
		}
		if(salesOrder.getStatus() == SalesOrderStatusEnum.CANCELED) {
			log.info("Client tried sale with a sales order already canceled.");
			throw new SalesOrderProcessingException("This sales order is canceled!");
		}
		if(salesOrder.getStatus() == SalesOrderStatusEnum.FINISHED) {
			log.info("Client tried sale with a sales order already finshed.");
			throw new SalesOrderProcessingException("This sales order is finished!");
		}
		if(salesOrder.getBag().isEmpty()) {
			log.info("Client tried sale with a empty bag.");
			throw new SalesOrderProcessingException("This sales order is empty!");
		}
		if(salesOrder.getPayment()==null) {
			log.info("Client tried sale without payment register.");
			throw new SalesOrderProcessingException("This sales order payment is null!");
		}
		
		boolean isValid = true;
		for(ProductSalesOrder pso : salesOrder.getBag()) {
			Product productinStock = productService.searchBySku(pso.getSku());
			if(pso.getAmount() > productinStock.getAmount()) {
				isValid = false;
			}
		}
		
		if(!isValid) {
			salesOrder.setStatus(SalesOrderStatusEnum.OPEN);
			salesOrderRepository.save(salesOrder);
			throw new SalesOrderProcessingException("This sales oder must be processed again!");
		}
		
		return true;
	}
	
	public boolean salesOrderCanBeCanceled(SalesOrder salesOrder) {
		if(salesOrder.getStatus() == SalesOrderStatusEnum.FINISHED) {
			log.info("Client tried cancel a finished sales order.");
			throw new SalesOrderCancellationException("It is not possible to cancel an already finished sales order!");
		}
		if(salesOrder.getStatus() == SalesOrderStatusEnum.CANCELED) {
			log.info("Client tried cancel a canceled sales order.");
			throw new SalesOrderCancellationException("This sales order has already been canceled!");
		}
		return true;
	}
}
