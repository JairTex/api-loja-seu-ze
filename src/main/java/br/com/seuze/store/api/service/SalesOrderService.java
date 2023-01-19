package br.com.seuze.store.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seuze.store.api.model.SalesOrder;
import br.com.seuze.store.api.repository.SalesOrderRepository;

@Service
public class SalesOrderService {
	@Autowired
	SalesOrderRepository salesOrderRepository;
	
	public SalesOrder createSalesOrder() {
		return salesOrderRepository.save(new SalesOrder());
	}
}
