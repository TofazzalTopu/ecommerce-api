package com.info.ecommerce.service;


import com.info.ecommerce.model.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();

    Item save(Item item);

    Item findById(Long id);
    List<Item> findByIds(List<Long> ids);

    Item update(Long id, Item item);

    void delete(Long id);

    Item findByName(String name);

    Item findBySku(String sku);
}
