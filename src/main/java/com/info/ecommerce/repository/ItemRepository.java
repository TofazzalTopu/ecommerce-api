package com.info.ecommerce.repository;

import com.info.ecommerce.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByIdIn(List<Long> ids);
    Item findByName(String name);
    Item findBySku(String sku);
}
