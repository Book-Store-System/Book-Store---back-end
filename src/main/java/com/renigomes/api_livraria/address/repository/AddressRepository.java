package com.renigomes.api_livraria.address.repository;

import com.renigomes.api_livraria.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(long userId);

    Long countAddressByUserId(long userId);

    @Query(value = "SELECT * FROM address WHERE user_id = :userId AND address_default = true",
            nativeQuery = true)
    Address findAddressDefaultByUserId(long userId);
}
