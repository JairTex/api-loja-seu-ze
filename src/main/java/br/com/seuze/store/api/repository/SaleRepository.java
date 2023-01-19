package br.com.seuze.store.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.seuze.store.api.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
