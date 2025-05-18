package com.renigomes.api_livraria.order_package.cupom.model;

import com.renigomes.api_livraria.order_package.cupom.enums.TypeCupom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "cupom")
@Setter
@Getter
public class Cupom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable = false)
    private Boolean active;
    private String codeCupom;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_cupom")
    private TypeCupom typeCupom;
    @Column(name = "percent_discount")
    private Double percentDiscount;
}
