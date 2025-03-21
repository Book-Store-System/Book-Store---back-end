package com.renigomes.api_livraria.address.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String neighborhood;
    private String CEP;
    private String reference;
    @Column(name = "address_default")
    private Boolean addressDefault;

}
