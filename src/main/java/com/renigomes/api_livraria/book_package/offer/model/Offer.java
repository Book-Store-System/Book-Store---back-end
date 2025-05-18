package com.renigomes.api_livraria.book_package.offer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "offer")
@Setter
@Getter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column (insertable = false)
    private Boolean active;
    private Double percent;
}
