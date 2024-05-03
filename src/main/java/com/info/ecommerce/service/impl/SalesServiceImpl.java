package com.info.ecommerce.service.impl;

import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.ItemResponseDTO;
import com.info.ecommerce.dto.SalesDTO;
import com.info.ecommerce.exception.NotAllowedException;
import com.info.ecommerce.exception.NotFoundException;
import com.info.ecommerce.mapper.SalesItemMapper;
import com.info.ecommerce.model.Customer;
import com.info.ecommerce.model.Sales;
import com.info.ecommerce.model.SalesItem;
import com.info.ecommerce.repository.SalesRepository;
import com.info.ecommerce.service.CustomerService;
import com.info.ecommerce.service.SalesItemService;
import com.info.ecommerce.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
public class SalesServiceImpl implements SalesService {

    private SalesRepository salesRepository;
    private CustomerService customerService;
    private SalesItemService salesItemService;
    private SalesItemMapper salesItemMapper;

    public SalesServiceImpl(SalesRepository salesRepository, CustomerService customerService, SalesItemService salesItemService, SalesItemMapper salesItemMapper) {
        this.salesRepository = salesRepository;
        this.customerService = customerService;
        this.salesItemService = salesItemService;
        this.salesItemMapper = salesItemMapper;
    }

    @Override
    public List<Sales> findAll() {
        return salesRepository.findAll(Sort.by("id").descending().and(Sort.by("customer").ascending()));
    }

    @Override
    public Sales findById(Long id) {
        if (log.isDebugEnabled()) {
            log.debug("id: {}", id);
        }
        return salesRepository.findById(id).orElseThrow(() -> new NotFoundException("Sales ID " + id + " " + AppConstants.NOT_FOUND));
    }

    @Override
    public Sales save(SalesDTO salesDTO) throws Exception {
        validate(salesDTO);
        Customer customer = customerService.findById(salesDTO.getCustomerId());
        Sales sales = salesRepository.save(new Sales(customer));
        List<SalesItem> salesItems = salesItemMapper.itemDTOToSalesItem(salesDTO.getItemDTOS(), sales);
        salesItems = salesItemService.saveAll(salesItems);
        sales.setSalesItems(salesItems);
        return sales;
    }


    @Override
    public Sales update(Long id, SalesDTO salesDTO) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("salesDTO: {}", salesDTO);
        }
        validate(salesDTO);
        Sales existing = findById(id);
        existing.setCustomer(customerService.findById(salesDTO.getCustomerId()));
        Sales sales = salesRepository.save(existing);

        salesItemService.deleteBySalesId(id);
        List<SalesItem> salesItems = salesItemMapper.itemDTOToSalesItem(salesDTO.getItemDTOS(), sales);
        salesItems = salesItemService.saveAll(salesItems);
        sales.setSalesItems(salesItems);
        return sales;
    }

    @Override
    public Double findCurrentDateSalesAmount() {
        List<Sales> salesList = salesRepository.findAllByCreatedAtOrderByCreatedAtDesc(LocalDate.now());
        return salesList.stream().flatMap(s -> s.getSalesItems().stream()).mapToDouble(SalesItem::getTotalPrice).sum();
    }

    @Override
    public LocalDate findMaxSalesDateInGivenTimeRange(LocalDate startDate, LocalDate endDate) {
        return salesRepository.findMaxSalesDateInGivenTimeRange(startDate, endDate);
    }

    @Override
    public List<ItemResponseDTO> findTop5SalesItemsBasedOnTotalSalesAmount() {
        return salesRepository.findTop5SalesItemsBasedOnTotalSalesAmount();
    }

    @Override
    public List<ItemResponseDTO> findTop5SalesItemsOfLastMonthBasedOnTotalNumberOfSales(LocalDate startDate, LocalDate endDate) {
        return salesRepository.findTop5SalesItemsOfLastMonthBasedOnMaxTotalSales(startDate, endDate);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        salesRepository.deleteById(id);
    }

    private void validate(SalesDTO salesDTO) {
        if (salesDTO.getItemDTOS().isEmpty()) {
            throw new NotAllowedException(AppConstants.SALES_ITEMS_CAN_NOT_BE_EMPTY);
        }
    }

}
