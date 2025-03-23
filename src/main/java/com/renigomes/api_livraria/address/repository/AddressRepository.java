package com.renigomes.api_livraria.address.repository;

import com.renigomes.api_livraria.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(long userId);
}
