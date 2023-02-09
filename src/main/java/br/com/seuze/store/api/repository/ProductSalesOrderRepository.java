package br.com.seuze.store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.seuze.store.api.model.ProductSalesOrder;

public interface ProductSalesOrderRepository  extends JpaRepository<ProductSalesOrder, Long> {

}
