package com.renigomes.api_livraria.delivery_control.model;

import com.renigomes.api_livraria.delivery_control.enums.OrderStatus;
import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "delivery_control")
@Getter
@Setter
public class DeliveryControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "order_id")
    private PurchaseOrder order;

    private String transportation;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus status;

    @Column(name = "delivered_on")
    private LocalDate deliveredOn;


}
