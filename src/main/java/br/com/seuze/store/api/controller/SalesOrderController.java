package br.com.seuze.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.seuze.store.api.enumerations.PaymentMethodEnum;
import br.com.seuze.store.api.enumerations.PixKeyTypeEnum;
import br.com.seuze.store.api.exceptions.PaymentMethodNotFoundException;
import br.com.seuze.store.api.exceptions.PixPaymentException;
import br.com.seuze.store.api.service.SalesOrderService;
import br.com.seuze.store.api.strategies.CashPaymentStrategy;
import br.com.seuze.store.api.strategies.CreditCardPaymentStrategy;
import br.com.seuze.store.api.strategies.DebitCardPaymentStrategy;
import br.com.seuze.store.api.strategies.PixPaymentStrategy;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sales-order")
public class SalesOrderController {
	@Autowired
	SalesOrderService salesOrderService;
	
	@ApiOperation("API to list all sales order.")
	@GetMapping("")
	//@ResponseStatus(HttpStatus.OK) If not use ResponseEntity
	public ResponseEntity<?> getAllSalesOrder() {
		return new ResponseEntity<>(salesOrderService.listAllSalesOrder(),
					HttpStatus.OK);
		
	}
	
	@ApiOperation("API open a new sales order.")
	@PostMapping("/create")
	public ResponseEntity<?> create() {
		return new ResponseEntity<>(salesOrderService.createSalesOrder(),
					HttpStatus.CREATED);
	}
	
	@ApiOperation("API to find a sales order by id.")
	@GetMapping("/{id}")
	public ResponseEntity<?> searchById(@PathVariable Long id) {
		return new ResponseEntity<>(salesOrderService.searchById(id),
					HttpStatus.OK);
	}
	
	@ApiOperation("API to add a document to a sales order.")
	@PostMapping("/add-document/{id}")
	public ResponseEntity<?> addDocument(@PathVariable("id") Long id, @RequestBody ObjectNode objectNode) {
		return new ResponseEntity<>(salesOrderService.addDocument(id, 
					objectNode.get("document").asText()),
					HttpStatus.OK);
		
	}
	
	@ApiOperation("API to process a sales order before sale.")
	@PostMapping("/process/{id}")
	public ResponseEntity<?> processSalesOrder(@PathVariable("id") Long id) {
		return new ResponseEntity<>(salesOrderService.processSalesOrder(id),
				HttpStatus.OK);
	}
	
	@ApiOperation("API to sell the products from a sales order.")
	@PostMapping("/sell/{id}")
	public ResponseEntity<?> sell(@PathVariable("id") Long id) {
 		return new ResponseEntity<>(salesOrderService.sell(id),
					HttpStatus.OK);
	}
	
	@ApiOperation("API to register a payment method on a sales order before sale.")
	@PostMapping("/{paymentMethod}/{id}")
	public ResponseEntity<?> processSalesOrderPayment(@PathVariable("id") Long id,
			@PathVariable("paymentMethod") String paymentMethod, @RequestBody ObjectNode objectNode) {
		switch(paymentMethod) {
			case("cash"):{
				CashPaymentStrategy payment = new CashPaymentStrategy();
				payment.setOrderId(id);
				
				return new ResponseEntity<>(salesOrderService.addPaymentMethodCash(id, payment),
							HttpStatus.OK);
			}
			case("pix"):{
				PixPaymentStrategy payment = new PixPaymentStrategy();
				
				String pixKeyType = objectNode.get("pixKeyType").asText().toUpperCase();
				if(!PixKeyTypeEnum.getTypeList().contains(pixKeyType)) {
					throw new PixPaymentException("This pix key type is unknown: " + pixKeyType 
							+". Try one of these pix key types: " + PixKeyTypeEnum.getTypeList());
				}
				
				PixKeyTypeEnum pixKeyMethod = PixKeyTypeEnum.valueOf(pixKeyType);
				
				String pixKey = objectNode.get("pixKey").asText();
				payment.setOrderId(id);
				payment.setPixKeyType(pixKeyMethod);
				payment.setKey(pixKey);
				return new ResponseEntity<>(salesOrderService.addPaymentMethodPix(id, payment),
							HttpStatus.OK);
			}
			case("debit-card"):{
				DebitCardPaymentStrategy payment = new DebitCardPaymentStrategy();
				payment.setOrderId(id);
				payment.setCardSecurity(objectNode.get("cardSecurity").asText());
				payment.setCardNumber(objectNode.get("cardNumber").asText());
				payment.setExpirationDate(objectNode.get("expirationDate").asText());
				
				return new ResponseEntity<>(salesOrderService.addPaymentMethodCard(id, payment),
							HttpStatus.OK);
			}
			case("credit-card"):{
				CreditCardPaymentStrategy payment = new CreditCardPaymentStrategy();
				payment.setOrderId(id);
				payment.setCardSecurity(objectNode.get("cardSecurity").asText());
				payment.setCardNumber(objectNode.get("cardNumber").asText());
				payment.setExpirationDate(objectNode.get("expirationDate").asText());
				
				return new ResponseEntity<>(salesOrderService.addPaymentMethodCard(id, payment),
							HttpStatus.OK);
			}
			default:{
				throw new PaymentMethodNotFoundException("Invalid payment method: " + paymentMethod 
						+ ". Try one of these methods: " + PaymentMethodEnum.getTypeList());
			}
		}
	}
	
	@ApiOperation("API to cancel an open or processed sales order.")
	@PostMapping("/cancel/{id}")
	public ResponseEntity<?> cancelSalesOrder(@PathVariable("id") Long id) {
		return new ResponseEntity<>(salesOrderService.cancelSalesOrder(id),
					HttpStatus.OK);
	}
}
