package com.info.ecommerce.repository;

import com.info.ecommerce.model.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesItemRepository extends JpaRepository<SalesItem, Long> {

    List<SalesItem> findAllBySalesId(Long salesId);

}
