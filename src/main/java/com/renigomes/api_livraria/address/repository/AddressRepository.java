package com.renigomes.api_livraria.address.repository;

import com.renigomes.api_livraria.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
