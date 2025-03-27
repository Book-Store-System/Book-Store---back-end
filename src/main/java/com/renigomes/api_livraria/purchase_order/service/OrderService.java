package com.renigomes.api_livraria.purchase_order.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.dto.BookRespOrderDto;
import com.renigomes.api_livraria.purchase_order.DTO.ItemOrderRespDto;
import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.purchase_order.DTO.OrderRespDto;
import com.renigomes.api_livraria.purchase_order.exceptions.ItemOrderException;
import com.renigomes.api_livraria.purchase_order.exceptions.OrderNotFound;
import com.renigomes.api_livraria.purchase_order.model.ItemOrder;
import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.purchase_order.repository.PurOrderRepository;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;
    private PurOrderRepository purOrderRepository;
    private UserComponent userComponent;
    private ItemOrderService itemOrderService;

    private List<ItemOrderRespDto> getItems(PurchaseOrder purOrder) {
        return purOrder.getItemOrders()
                .stream()
                .map(
                        item -> {
                            BookRespOrderDto bookRespOrderDto = modelMapper.map(
                                    item.getBookStock().getBook(), BookRespOrderDto.class
                            );
                            bookRespOrderDto.setPrice(item.getBookStock().getPurchasePrice()
                                    .add(
                                            item.getBookStock().getPurchasePrice().multiply(
                                                    BigDecimal.valueOf(item.getBookStock().getProfitMargin())
                                            )
                                    ));
                            return new ItemOrderRespDto(
                                    bookRespOrderDto,
                                    item.getQuantity(),
                                    calculateSubTotal(item)
                            );
                        }
                ).toList();
    }

    private BigDecimal calcTotalValue(List<ItemOrderRespDto> items, PurchaseOrder purOrder){
        return items
                .stream()
                .map(ItemOrderRespDto::getSubTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(purOrder.getShipping());
    }

    @Transactional
    public RespIdDto createOrder(OrderReqDTO orderReqDTO, HttpServletRequest request) {
        User user = userComponent.extractUserByToker(request);
        PurchaseOrder purOrder = modelMapper.map(orderReqDTO, PurchaseOrder.class);
        purOrder.setUser(user);
        purOrder.setOrderDate(LocalDate.now());
        purOrder = purOrderRepository.save(purOrder);
        PurchaseOrder finalPurOrder = purOrder;
        orderReqDTO.getItems()
                .forEach(item -> {
                    if (itemOrderService.createItemOrder(item, finalPurOrder)==null){
                        log.error("Item Order could not be created");
                        throw new ItemOrderException("Internal error creating item order", HttpStatus.INTERNAL_SERVER_ERROR);
                    };
                });
        return new RespIdDto(finalPurOrder.getId());
    }

    @Transactional
    public void save(PurchaseOrder purchaseOrder) {
        purOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> findByUser(User user) {
        return purOrderRepository.findByUser(user);
    }

    private BigDecimal calculateSubTotal(ItemOrder item){
        return BigDecimal.valueOf(item.getQuantity())
                .multiply(
                        item.getBookStock().getPurchasePrice()
                                .add(
                                        item.getBookStock().getPurchasePrice().multiply(
                                                BigDecimal.valueOf(item.getBookStock().getProfitMargin())
                                        )
                                )
                );
    }

    public OrderRespDto findById(Long id){
        PurchaseOrder purchaseOrder = purOrderRepository.findById(id).orElseThrow(
                () ->{
                    log.error("Order not found");
                    return new OrderNotFound("Order not found", HttpStatus.NOT_FOUND);
                }
        );
        OrderRespDto orderRespDto = modelMapper.map(purchaseOrder, OrderRespDto.class);
        orderRespDto.setItems(getItems(purchaseOrder));
        orderRespDto.setTotalValue(calcTotalValue(orderRespDto.getItems(), purchaseOrder));
        return orderRespDto;
    }

    public List<OrderRespDto> findByOrderUser(HttpServletRequest request) {
        User user = userComponent.extractUserByToker(request);
        List<PurchaseOrder> purchaseOrders = purOrderRepository.findByUser(user);
        return purchaseOrders
                .stream()
                .map(
                        purOrder -> {
                            List<ItemOrderRespDto> items = getItems(purOrder);
                            OrderRespDto orderRespDto = modelMapper.map(purOrder, OrderRespDto.class);
                            orderRespDto.setItems(items);
                            orderRespDto.setTotalValue(calcTotalValue(items, purOrder));
                            return orderRespDto;
                        }
                ).toList();
    }
}
