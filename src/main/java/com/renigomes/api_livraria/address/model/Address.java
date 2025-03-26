package com.renigomes.api_livraria.address.model;

import com.renigomes.api_livraria.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    private Boolean addressDefault;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}
