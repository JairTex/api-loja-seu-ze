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

import br.com.seuze.store.api.enumerations.PixKeyTypeEnum;
import br.com.seuze.store.api.service.SalesOrderService;
import br.com.seuze.store.api.strategies.CashPaymentStrategy;
import br.com.seuze.store.api.strategies.CreditCardPaymentStrategy;
import br.com.seuze.store.api.strategies.DebitCardPaymentStrategy;
import br.com.seuze.store.api.strategies.PixPaymentStrategy;

@RestController
@RequestMapping("seu-ze-store/")
public class SalesOrderController {
	@Autowired
	SalesOrderService salesOrderService;
	
	@GetMapping("sales-order/")
	//@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAllSalesOrder() {
		try {
			return new ResponseEntity<>(salesOrderService.listAllSalesOrder(),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("sales-order/create/")
	public ResponseEntity<?> create() {
		try {
			return new ResponseEntity<>(salesOrderService.createSalesOrder(),
					HttpStatus.CREATED);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("sales-order/{id}")
	public ResponseEntity<?> searchById(@PathVariable Long id) {
		try {
			return new ResponseEntity<>(salesOrderService.searchById(id),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("sales-order/add-document-to-order/{id}")
	public ResponseEntity<?> addDocument(@PathVariable("id") Long id, @RequestBody ObjectNode objectNode) {
		try {
			return new ResponseEntity<>(salesOrderService.addDocument(id, 
					objectNode.get("document").asText()),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("sales-order/process/{id}")
	public ResponseEntity<?> processOrderSale(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(salesOrderService.processOrderSale(id),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("sales-order/sell/{id}")
	public ResponseEntity<?> sell(@PathVariable("id") Long id) {
		try {
 			return new ResponseEntity<>(salesOrderService.sell(id),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("sales-order/{paymentMethod}/{id}")
	public ResponseEntity<?> processSalesOrder(@PathVariable("id") Long id,
			@PathVariable("paymentMethod") String paymentMethod, @RequestBody ObjectNode objectNode) {
		switch(paymentMethod) {
			case("cash"):{
				CashPaymentStrategy payment = new CashPaymentStrategy();
				payment.setOrderId(id);
				try {
					return new ResponseEntity<>(salesOrderService.addPaymentMethodCash(id, payment),
							HttpStatus.OK);
				}catch (RuntimeException e) {
					return new ResponseEntity<>(e.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
			}
			case("pix"):{
				PixPaymentStrategy payment = new PixPaymentStrategy();
				PixKeyTypeEnum pixKeyMethod = PixKeyTypeEnum.valueOf(objectNode.get("pixKeyType").asText().toUpperCase());
				
				String pixKey = objectNode.get("pixKey").asText();
				payment.setOrderId(id);
				payment.setPixKeyType(pixKeyMethod);
				payment.setKey(pixKey);
				try {
					return new ResponseEntity<>(salesOrderService.addPaymentMethodPix(id, payment),
							HttpStatus.OK);
				}catch (RuntimeException e) {
					return new ResponseEntity<>(e.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
			}
			case("debit-card"):{
				DebitCardPaymentStrategy payment = new DebitCardPaymentStrategy();
				payment.setOrderId(id);
				payment.setCardSecurity(objectNode.get("cardSecurity").asText());
				payment.setCardNumber(objectNode.get("cardNumber").asText());
				payment.setExpirationDate(objectNode.get("expirationDate").asText());
				
				try {
					return new ResponseEntity<>(salesOrderService.addPaymentMethodCard(id, payment),
							HttpStatus.OK);
				}catch (RuntimeException e) {
					return new ResponseEntity<>(e.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
			}
			case("credit-card"):{
				CreditCardPaymentStrategy payment = new CreditCardPaymentStrategy();
				payment.setOrderId(id);
				payment.setCardSecurity(objectNode.get("cardSecurity").asText());
				payment.setCardNumber(objectNode.get("cardNumber").asText());
				payment.setExpirationDate(objectNode.get("expirationDate").asText());
				
				try {
					return new ResponseEntity<>(salesOrderService.addPaymentMethodCard(id, payment),
							HttpStatus.OK);
				}catch (RuntimeException e) {
					return new ResponseEntity<>(e.getMessage(),
							HttpStatus.BAD_REQUEST);
				}
			}
			default:{
				return new ResponseEntity<>("Invalid payment method: " + paymentMethod 
						+ "\nValids methods: cash, pix, debit-card e credit-card.",
						HttpStatus.BAD_REQUEST);
			}
		}
	}
	
	@PostMapping("sales-order/cancel/{id}")
	public ResponseEntity<?> cancelSalesOrder(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(salesOrderService.cancelSalesOrder(id),
					HttpStatus.OK);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}
}
