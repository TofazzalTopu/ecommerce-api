package com.info.ecommerce.mapper;

import com.info.ecommerce.dto.ItemRequestDTO;
import com.info.ecommerce.model.Sales;
import com.info.ecommerce.model.SalesItem;

import java.util.List;

public interface SalesItemMapper {

    List<SalesItem> mapSalesToSalesItem(Sales sales);

    List<SalesItem> mapSalesItem(List<SalesItem> salesItemList);

    List<SalesItem> itemDTOToSalesItem(List<ItemRequestDTO> itemDTOS, Sales sales);
}
