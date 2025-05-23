package com.renigomes.api_livraria.unitaries_tests.purchase_order.service;

import com.renigomes.api_livraria.order_package.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.order_package.purchase_order.repository.PurOrderRepository;
import com.renigomes.api_livraria.order_package.purchase_order.service.OrderService;
import com.renigomes.api_livraria.user_package.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PurOrderRepository purOrderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
    }

    @Test
    void save() {
    }

    @Test
    void findByUser() {
        User user = new User();
        List<PurchaseOrder>  orderList = List.of(new PurchaseOrder(), new PurchaseOrder());
        when(purOrderRepository.findByUser(user)).thenReturn(orderList);
        List<PurchaseOrder> result = orderService.findByUser(user);
        assertNotNull(result);
        verify(purOrderRepository).findByUser(user);
    }

    @Test
    void sampleFindOrderById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByOrderUser() {
    }

    @Test
    void addCupom() {
    }
}