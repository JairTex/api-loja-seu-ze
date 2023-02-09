package br.com.seuze.store.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seuze.store.api.model.Sale;
import br.com.seuze.store.api.repository.SaleRepository;

@Service
public class SaleService implements SaleServiceInterface{
	@Autowired
	SaleRepository saleRepository;
	
	public List<Sale> listAllSales() {
		return saleRepository.findAll();
	}
}
