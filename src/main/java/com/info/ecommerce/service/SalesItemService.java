package com.info.ecommerce.service;

import com.info.ecommerce.model.SalesItem;

import java.util.List;

public interface SalesItemService {

    SalesItem save(SalesItem salesItem);

    List<SalesItem> saveAll(List<SalesItem> salesItems);

    SalesItem findById(Long id);

    List<SalesItem> findBySalesId(Long salesId);

    void delete(Long id);

    void deleteBySalesId(Long salesId);


}
