package br.com.seuze.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.seuze.store.api.service.SaleService;

@RestController
@RequestMapping("seu-ze-store/")
public class SalesController {
	@Autowired
	SaleService saleService;
	
	@GetMapping("sales/")
	public ResponseEntity<?> getAllSalesOrder() {
		try {
			return new ResponseEntity<>(saleService.listAllSales(),
					HttpStatus.CREATED);
		}catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		//return saleService.listAllSalesOrder();
	}
}
