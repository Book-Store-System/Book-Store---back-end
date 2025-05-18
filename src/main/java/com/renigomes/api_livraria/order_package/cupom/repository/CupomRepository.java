package com.renigomes.api_livraria.order_package.cupom.repository;

import com.renigomes.api_livraria.order_package.cupom.model.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CupomRepository extends JpaRepository<Cupom, Long> {
    @Query(value = "SELECT * FROM cupom WHERE code_cupom=:codigo", nativeQuery = true)
    Optional<Cupom> findByCode(String codigo);
}
