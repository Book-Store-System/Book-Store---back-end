package com.renigomes.api_livraria.order_package.purchase_order.repository;

import com.renigomes.api_livraria.order_package.purchase_order.model.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, Integer> {

}