package com.renigomes.api_livraria.purchase_order.model;

import com.renigomes.api_livraria.cart.model.Cart;
import com.renigomes.api_livraria.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "purchase_order")
@Getter
@Setter
public class PunchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @OneToOne
    private Cart cart;
    private String payment;
    private BigDecimal shipping;
    @Column(name = "total_value")
    private BigDecimal totalValue;
}
