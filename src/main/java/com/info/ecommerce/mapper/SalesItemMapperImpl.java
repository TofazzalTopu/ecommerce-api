package com.info.ecommerce.mapper;

import com.info.ecommerce.dto.ItemRequestDTO;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.model.Sales;
import com.info.ecommerce.model.SalesItem;
import com.info.ecommerce.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesItemMapperImpl implements SalesItemMapper {

    @Autowired
    private ItemService itemService;

    @Override
    public List<SalesItem> mapSalesToSalesItem(Sales sales) {
        List<SalesItem> salesItems = sales.getSalesItems();
        salesItems.forEach(i -> {
            Item item = itemService.findById(i.getItem().getId());
            i.setSales(sales);
            i.setTotalPrice(i.getQuantity() * item.getUnitPrice());
        });
        return salesItems;
    }

    @Override
    public List<SalesItem> mapSalesItem(List<SalesItem> salesItemList) {
        salesItemList.forEach((e -> e.setSales(null)));
        return salesItemList;
    }

    @Override
    public List<SalesItem> itemDTOToSalesItem(List<ItemRequestDTO> itemDTOS, Sales sales) {
        List<SalesItem> salesItems = new ArrayList<>();
        itemDTOS.forEach((e -> {
            Item item = itemService.findById(e.getItemId());
            SalesItem salesItem = new SalesItem();
            salesItem.setSales(sales);
            salesItem.setItem(item);
            salesItem.setQuantity(e.getQuantity());
            salesItem.setTotalPrice(item.getUnitPrice() * e.getQuantity());
            salesItems.add(salesItem);
        }));

        return salesItems;
    }
}
