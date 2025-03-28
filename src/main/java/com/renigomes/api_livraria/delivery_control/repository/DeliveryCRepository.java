package com.renigomes.api_livraria.delivery_control.repository;

import com.renigomes.api_livraria.delivery_control.model.DeliveryControl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryCRepository extends JpaRepository<DeliveryControl, Long> {
}
