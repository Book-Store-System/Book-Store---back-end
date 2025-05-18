package com.renigomes.api_livraria.order_package.delivery_control.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.order_package.delivery_control.DTO.DeliveredRespDto;
import com.renigomes.api_livraria.order_package.delivery_control.DTO.DeliveryReqDto;
import com.renigomes.api_livraria.order_package.delivery_control.DTO.SetTransportReqDto;
import com.renigomes.api_livraria.order_package.delivery_control.DTO.SetTransportationRespDto;
import com.renigomes.api_livraria.order_package.delivery_control.enums.OrderStatus;
import com.renigomes.api_livraria.order_package.delivery_control.exceptions.DeliveryNotFoundException;
import com.renigomes.api_livraria.order_package.delivery_control.model.DeliveryControl;
import com.renigomes.api_livraria.order_package.delivery_control.repository.DeliveryCRepository;
import com.renigomes.api_livraria.order_package.purchase_order.service.OrderService;
import com.renigomes.api_livraria.user_package.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class DeliveryConService {

    private final DeliveryCRepository deliveryCRepository;
    private final UserService userService;
    private final OrderService orderService;

    private DeliveryNotFoundException deliveryNotFound(){
        log.error("Delivery control not found !");
        return new DeliveryNotFoundException("Delivery control not found !", HttpStatus.NOT_FOUND);
    }

    public DeliveredRespDto findDeliveryControl(Long id_order) {
        DeliveryControl deliveryControl = deliveryCRepository.findByOrderId(id_order)
                .orElseThrow(
                        this::deliveryNotFound
                );
        return new DeliveredRespDto(
                deliveryControl.getUser().getId(),
                deliveryControl.getOrder().getId(),
                deliveryControl.getTransportation(),
                deliveryControl.getStatus().name(),
                deliveryControl.getDeliveredOn()
        );
    }

    @Transactional
    public RespIdDto createDeliControl(DeliveryReqDto deliveryReqDto) {
        DeliveryControl deliveryControl = new DeliveryControl();
        deliveryControl
                .setUser(
                        userService.findById(deliveryReqDto.userId())
                );
        deliveryControl.setOrder(
                orderService.sampleFindOrderById(deliveryReqDto.orderId())
        );
        deliveryControl.setStatus(OrderStatus.PREPARING);
        deliveryControl = deliveryCRepository.save(deliveryControl);
        return new RespIdDto(deliveryControl.getId());
    }

    @Transactional
    public SetTransportationRespDto setTransportation(Long id_order, SetTransportReqDto transportation) {
        DeliveryControl deliveryControl = deliveryCRepository.findByOrderId(id_order)
                .orElseThrow(
                        this::deliveryNotFound
                );
        deliveryControl.setStatus(OrderStatus.ON_THE_WAY);
        BeanUtils.copyProperties(transportation, deliveryControl);
        deliveryControl = deliveryCRepository.save(deliveryControl);
        return new SetTransportationRespDto(
                deliveryControl.getUser().getId(),
                deliveryControl.getOrder().getId(),
                deliveryControl.getTransportation(),
                deliveryControl.getStatus()
        );
    }

    @Transactional
    public DeliveredRespDto setDelivered(Long id_order) {
        DeliveryControl deliveryControl = deliveryCRepository.findByOrderId(id_order)
                .orElseThrow(
                        this::deliveryNotFound
                );
        deliveryControl.setStatus(OrderStatus.DELIVERED);
        deliveryControl.setDeliveredOn(LocalDate.now());
        deliveryControl = deliveryCRepository.save(deliveryControl);
        return new DeliveredRespDto(
                deliveryControl.getUser().getId(),
                deliveryControl.getOrder().getId(),
                deliveryControl.getTransportation(),
                deliveryControl.getStatus().name(),
                deliveryControl.getDeliveredOn()
        );
    }

}
