package com.renigomes.api_livraria.user_package.address.model;

import com.renigomes.api_livraria.user_package.user.model.User;
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
    private String state;
    private String neighborhood;
    private String CEP;
    private Integer number;
    private String reference;
    @Column(name = "address_default")
    private boolean addressDefault;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
