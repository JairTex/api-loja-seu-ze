package br.com.seuze.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.seuze.store.api.service.SaleService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sales")
public class SaleController {
	@Autowired
	SaleService saleService;
	
	@ApiOperation("API to list sales made.")
	@GetMapping("")
	public ResponseEntity<?> getAllSalesOrder() {
		return new ResponseEntity<>(saleService.listAllSales(),
					HttpStatus.OK);
	}
}
