package br.com.seuze.store.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seuze.store.api.exceptions.SaleNotFoundException;
import br.com.seuze.store.api.model.Sale;
import br.com.seuze.store.api.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleService implements SaleServiceInterface{
	@Autowired
	SaleRepository saleRepository;
	
	public List<Sale> listAllSales() {
		if(!saleRepository.findAll().isEmpty()) {
			return saleRepository.findAll();
		}else {
			log.info("Sales searched by client not found!");
			throw new SaleNotFoundException("There are no registered Sales!");
		}
	}
}
