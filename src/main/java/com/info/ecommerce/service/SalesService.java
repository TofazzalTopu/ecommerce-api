package com.info.ecommerce.service;

import com.info.ecommerce.dto.ItemResponseDTO;
import com.info.ecommerce.dto.SalesDTO;
import com.info.ecommerce.model.Sales;

import java.time.LocalDate;
import java.util.List;

public interface SalesService {

    List<Sales> findAll();

    Sales findById(Long id);

    Sales save(SalesDTO salesDTO) throws Exception;

    Sales update(Long id, SalesDTO salesDTO) throws Exception;

    Double findCurrentDateSalesAmount();

    LocalDate findMaxSalesDateInGivenTimeRange(LocalDate startDate, LocalDate endDate);

    List<ItemResponseDTO> findTop5SalesItemsBasedOnTotalSalesAmount();

    List<ItemResponseDTO> findTop5SalesItemsOfLastMonthBasedOnTotalNumberOfSales(LocalDate startDate, LocalDate endDate);

    void delete(Long id);

}
