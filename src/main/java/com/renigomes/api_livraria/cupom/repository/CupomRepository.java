package com.renigomes.api_livraria.cupom.repository;

import com.renigomes.api_livraria.cupom.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupomRepository extends JpaRepository<Cupom, Long> {
}
