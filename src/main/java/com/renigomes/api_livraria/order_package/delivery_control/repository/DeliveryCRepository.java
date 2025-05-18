package com.renigomes.api_livraria.order_package.delivery_control.repository;

import com.renigomes.api_livraria.order_package.delivery_control.model.DeliveryControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryCRepository extends JpaRepository<DeliveryControl, Long> {

    Optional<DeliveryControl> findByOrderId(Long orderId);
}
