package com.renigomes.api_livraria.order_package.purchase_order.model;

import com.renigomes.api_livraria.order_package.cupom.model.Cupom;
import com.renigomes.api_livraria.user_package.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "purchase_order")
@Getter
@Setter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String payment;
    private BigDecimal shipping;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOrder> itemOrders;
    @OneToOne
    @JoinColumn(name = "cupom_id")
    private Cupom cupom;

}
