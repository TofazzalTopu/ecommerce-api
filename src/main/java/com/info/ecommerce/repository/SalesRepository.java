package com.info.ecommerce.repository;

import com.info.ecommerce.dto.ItemResponseDTO;
import com.info.ecommerce.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    List<Sales> findAllByCreatedAtOrderByCreatedAtDesc(LocalDate localDate);

//    @Query(value = "SELECT SUM(si.totalPrice) AS amount " +
//            " FROM Sales s " +
//            " INNER JOIN SalesItem si ON(si.sales = s) " +
//            " WHERE s.createdAt = ?1 " +
//            " GROUP BY s.createdAt " +
//            " ORDER BY SUM(si.totalPrice)")
//    Double findTotalSalesAmountOfCurrentDate(LocalDate localDate);

    @Query(value = "SELECT s.createdAt" +
            " FROM Sales s " +
            " INNER JOIN SalesItem si ON(si.sales = s) " +
            " WHERE s.createdAt between ?1 AND ?2 " +
            " GROUP BY s.createdAt ORDER BY SUM(si.totalPrice) DESC LIMIT 1")
    LocalDate findMaxSalesDateInGivenTimeRange(LocalDate startLocalDate, LocalDate endLocalDate);

    @Query(value = "SELECT new com.info.ecommerce.dto.ItemResponseDTO(si.item.id, si.item.name, si.item.description, si.item.sku, SUM(si.quantity) AS quantity, si.item.unitPrice, SUM(si.totalPrice) AS totalPrice) " +
            " FROM Sales s " +
            " INNER JOIN SalesItem si ON(si.sales = s) " +
            " GROUP BY si.item ORDER BY totalPrice DESC LIMIT 5")
    List<ItemResponseDTO> findTop5SalesItemsBasedOnTotalSalesAmount();

    @Query(value = "SELECT new com.info.ecommerce.dto.ItemResponseDTO(si.item.id, si.item.name, si.item.description, si.item.sku, SUM(si.quantity) AS quantity, si.item.unitPrice, SUM(si.totalPrice) AS totalPrice) " +
            " FROM Sales s " +
            "INNER JOIN SalesItem si ON(si.sales = s) " +
            " WHERE s.createdAt between ?1 AND ?2 " +
            "GROUP BY si.item ORDER BY totalPrice DESC LIMIT 5")
    List<ItemResponseDTO> findTop5SalesItemsOfLastMonthBasedOnMaxTotalSales(LocalDate startLocalDate, LocalDate endLocalDate);

}
