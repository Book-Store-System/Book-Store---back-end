package com.renigomes.api_livraria.order_package.purchase_order.service;

import com.renigomes.api_livraria.book_package.book.service.BookStockService;
import com.renigomes.api_livraria.order_package.purchase_order.DTO.ItemOrderReqDto;
import com.renigomes.api_livraria.order_package.purchase_order.model.ItemOrder;
import com.renigomes.api_livraria.order_package.purchase_order.model.PurchaseOrder;
import com.renigomes.api_livraria.order_package.purchase_order.repository.ItemOrderRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ItemOrderService {

    private final ModelMapper modelMapper;
    private final ItemOrderRepository itemOrderRepository;
    private BookStockService bookStockService;

    @Transactional
    public ItemOrder createItemOrder(ItemOrderReqDto itemOrderReqDto, PurchaseOrder purchaseOrder) {
        ItemOrder itemOrder =  modelMapper.map(itemOrderReqDto, ItemOrder.class);
        itemOrder.setPurchaseOrder(purchaseOrder);
        itemOrder.setBookStock(bookStockService.findBookByID(itemOrderReqDto.getBookStockId()));
        itemOrder = itemOrderRepository.save(itemOrder);
        return itemOrder;
    }


}
