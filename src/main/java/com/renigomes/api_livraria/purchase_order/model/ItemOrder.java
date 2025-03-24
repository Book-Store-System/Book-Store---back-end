package com.renigomes.api_livraria.purchase_order.model;

import com.renigomes.api_livraria.book.model.BookStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "item_order")
@NoArgsConstructor
@Getter
@Setter
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private BookStock bookStock;
    @ManyToOne
    @JoinColumn(name="order_id")
    private PurchaseOrder purchaseOrder;
    private Integer quantity;
    @Column
    private Boolean isDeleted;

}
