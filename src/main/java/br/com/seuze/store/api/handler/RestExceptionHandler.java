package br.com.seuze.store.api.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.seuze.store.api.exceptions.CardDateException;
import br.com.seuze.store.api.exceptions.CardNumberException;
import br.com.seuze.store.api.exceptions.CardSecurityException;
import br.com.seuze.store.api.exceptions.ExceptionPatternDetails;
import br.com.seuze.store.api.exceptions.InvalidDocumentException;
import br.com.seuze.store.api.exceptions.InvalidEmailException;
import br.com.seuze.store.api.exceptions.InvalidProductException;
import br.com.seuze.store.api.exceptions.InvalidSalesOrderException;
import br.com.seuze.store.api.exceptions.PhoneNumberException;
import br.com.seuze.store.api.exceptions.PixPaymentException;
import br.com.seuze.store.api.exceptions.ProductNotFountException;
import br.com.seuze.store.api.exceptions.SaleNotFoundException;
import br.com.seuze.store.api.exceptions.SalesOrderCancellationException;
import br.com.seuze.store.api.exceptions.SalesOrderNotFoundException;
import br.com.seuze.store.api.exceptions.SalesOrderProcessingException;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(ProductNotFountException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerProductNotFountException(ProductNotFountException pnfe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Product not found!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(pnfe.getMessage())
				.developerMessage(pnfe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SaleNotFoundException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerSaleNotFoundException(SaleNotFoundException snfe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Product not found!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(snfe.getMessage())
				.developerMessage(snfe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CardDateException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerCardDateException(CardDateException cde){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid card date expiration!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(cde.getMessage())
				.developerMessage(cde.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CardNumberException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerCardNumberException(CardNumberException cne){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid card number")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(cne.getMessage())
				.developerMessage(cne.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CardSecurityException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerCardSecurityException(CardSecurityException cse){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid card security!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(cse.getMessage())
				.developerMessage(cse.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDocumentException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerInvalidDocumentException(InvalidDocumentException ide){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid document!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(ide.getMessage())
				.developerMessage(ide.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerInvalidEmailException(InvalidEmailException iee){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid email!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(iee.getMessage())
				.developerMessage(iee.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidProductException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerInvalidProductException(InvalidProductException ipe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid product!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(ipe.getMessage())
				.developerMessage(ipe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidSalesOrderException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerInvalidSalesOrderException(InvalidSalesOrderException isoe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid sales order!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(isoe.getMessage())
				.developerMessage(isoe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PhoneNumberException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerPhoneNumberException(PhoneNumberException pne){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Invalid phone number!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(pne.getMessage())
				.developerMessage(pne.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PixPaymentException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerPixPaymentException(PixPaymentException ppe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Payment processing error!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(ppe.getMessage())
				.developerMessage(ppe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SalesOrderNotFoundException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerSalesOrderNotFoundException(SalesOrderNotFoundException ponfe){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Sales Order not found!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(ponfe.getMessage())
				.developerMessage(ponfe.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SalesOrderProcessingException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerSalesOrderProcessingException(SalesOrderProcessingException sope){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Processing error!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(sope.getMessage())
				.developerMessage(sope.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SalesOrderCancellationException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerSalesOrderCancellationException(SalesOrderCancellationException soce){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Processing error!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(soce.getMessage())
				.developerMessage(soce.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionPatternDetails> 
	handlerRuntimeException(RuntimeException re){
		return new ResponseEntity<>(
				ExceptionPatternDetails.builder()
				.title("Runtime error!")
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.details(re.getMessage())
				.developerMessage(re.getClass().getName())
				.build(), HttpStatus.BAD_REQUEST);
	}
}
