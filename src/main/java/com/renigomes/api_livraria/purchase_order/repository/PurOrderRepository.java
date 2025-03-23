package com.renigomes.api_livraria.purchase_order.repository;

import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
