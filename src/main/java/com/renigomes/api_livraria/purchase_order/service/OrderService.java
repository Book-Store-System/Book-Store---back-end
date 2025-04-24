package com.renigomes.api_livraria.purchase_order.service;

import com.renigomes.api_livraria.DTO.RespIdDto;
import com.renigomes.api_livraria.book.dto.BookRespOrderDto;
import com.renigomes.api_livraria.cupom.DTO.CupomRespDto;
import com.renigomes.api_livraria.cupom.DTO.OrderCupomRespDto;
import com.renigomes.api_livraria.cupom.enums.TypeCupom;
import com.renigomes.api_livraria.cupom.model.Cupom;
import com.renigomes.api_livraria.cupom.service.CupomService;
import com.renigomes.api_livraria.purchase_order.DTO.ItemOrderRespDto;
import com.renigomes.api_livraria.purchase_order.DTO.OfferOrderRespDto;
import com.renigomes.api_livraria.purchase_order.DTO.OrderReqDTO;
import com.renigomes.api_livraria.purchase_order.DTO.OrderRespDto;
import com.renigomes.api_livraria.purchase_order.exceptions.ItemOrderException;
import com.renigomes.api_livraria.purchase_order.exceptions.OrderNotFound;
import com.renigomes.api_livraria.purchase_order.model.ItemOrder;
import com.renigomes.api_livraria.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.purchase_order.repository.PurOrderRepository;
import com.renigomes.api_livraria.user.component.UserComponent;
import com.renigomes.api_livraria.user.model.User;
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
    private ItemOrderService itemOrderService;
    private final CupomService cupomService;
    private final UserComponent userComponent;

    private OrderNotFound orderNotFound(){
        log.error("Order not found");
        return new OrderNotFound("Order not found", HttpStatus.NOT_FOUND);
    }

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
                            bookRespOrderDto.setOfferOrderRespDto(item.getBookStock().getOffer() == null?
                                    null:modelMapper.map(item.getBookStock().getOffer(), OfferOrderRespDto.class));
                            return new ItemOrderRespDto(
                                    bookRespOrderDto,
                                    item.getQuantity(),
                                    calculateSubTotal(item)
                                            .subtract(
                                                    item.getBookStock().getOffer() == null?
                                                            BigDecimal.ZERO: BigDecimal.valueOf(
                                                                    item.getBookStock().getOffer().getPercent()
                                                    ).multiply(calculateSubTotal(item))
                                            )
                            );
                        }
                ).toList();
    }

    private BigDecimal calcTotalValue(List<ItemOrderRespDto> items, PurchaseOrder purOrder){
        return items
                .stream()
                .map(ItemOrderRespDto::getSubTotalItem)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(purOrder.getShipping().subtract(
                        purOrder.getCupom()==null?BigDecimal.ZERO:
                                purOrder.getCupom().getTypeCupom() != TypeCupom.SHIPPING ?
                                BigDecimal.ZERO :
                                purOrder.getShipping().multiply(
                                        BigDecimal.valueOf(purOrder.getCupom().getPercentDiscount())
                                )
                ));
    }

    @Transactional
    public RespIdDto createOrder(OrderReqDTO orderReqDTO, Long id_user) {
        User user = userComponent.extractUser(id_user);
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

    public PurchaseOrder sampleFindOrderById(Long id){
        return purOrderRepository.findById(id).orElseThrow(
                this::orderNotFound
        );
    }

    public OrderRespDto findById(Long id){
        PurchaseOrder purchaseOrder = purOrderRepository.findById(id).orElseThrow(
                this::orderNotFound
        );
        OrderRespDto orderRespDto = modelMapper.map(purchaseOrder, OrderRespDto.class);
        orderRespDto.setItems(getItems(purchaseOrder));
        orderRespDto.setTotalValue(calcTotalValue(orderRespDto.getItems(), purchaseOrder)
                .subtract(
                        purchaseOrder.getCupom() == null ||
                                purchaseOrder.getCupom().getTypeCupom() != TypeCupom.PERCENTAGE ?
                                BigDecimal.ZERO :
                                calcTotalValue(orderRespDto.getItems(), purchaseOrder).multiply(
                                        BigDecimal.valueOf(purchaseOrder.getCupom().getPercentDiscount())
                                )

                ));
        orderRespDto.setCupom(purchaseOrder.getCupom()==null?null:new OrderCupomRespDto(purchaseOrder.getCupom().getCodeCupom()));
        return orderRespDto;
    }

    public List<OrderRespDto> findByOrderUser(Long id) {
        User user = userComponent.extractUser(id);
        List<PurchaseOrder> purchaseOrders = purOrderRepository.findByUser(user);
        return purchaseOrders
                .stream()
                .map(
                        purOrder -> {
                            List<ItemOrderRespDto> items = getItems(purOrder);
                            OrderRespDto orderRespDto = modelMapper.map(purOrder, OrderRespDto.class);
                            orderRespDto.setItems(items);
                            orderRespDto.setCupom(new OrderCupomRespDto(purOrder.getCupom().getCodeCupom()));
                            orderRespDto.setTotalValue(calcTotalValue(items, purOrder).subtract(
                                    purOrder.getCupom() == null ||
                                            purOrder.getCupom().getTypeCupom() != TypeCupom.PERCENTAGE ?
                                            BigDecimal.ZERO :
                                            calcTotalValue(items, purOrder).multiply(
                                                    BigDecimal.valueOf(purOrder.getCupom().getPercentDiscount())
                                            )

                            ));
                            return orderRespDto;
                        }
                ).toList();
    }

    @Transactional
    public CupomRespDto addCupom(Long id, String codeCupom){
        PurchaseOrder purchaseOrder = purOrderRepository.findById(id).orElseThrow(
                this::orderNotFound
        );
        Cupom cupom = cupomService.findCupomByCode(codeCupom);
        purchaseOrder.setCupom(cupom);
        purchaseOrder = purOrderRepository.save(purchaseOrder);
        return modelMapper.map(purchaseOrder.getCupom(), CupomRespDto.class);
    }


}
