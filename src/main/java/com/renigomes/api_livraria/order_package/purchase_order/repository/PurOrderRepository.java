package com.renigomes.api_livraria.order_package.purchase_order.repository;

import com.renigomes.api_livraria.order_package.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.user_package.user.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurOrderRepository extends JpaRepository<PurchaseOrder, Long> {
     List<PurchaseOrder> findByUser(User user);
}
