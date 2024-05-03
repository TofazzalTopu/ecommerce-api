package com.info.ecommerce.service.impl;

import com.info.ecommerce.AppConstants;
import com.info.ecommerce.exception.NotFoundException;
import com.info.ecommerce.model.SalesItem;
import com.info.ecommerce.repository.SalesItemRepository;
import com.info.ecommerce.service.SalesItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SalesItemServiceImpl implements SalesItemService {

    private SalesItemRepository salesItemRepository;

    public SalesItemServiceImpl(SalesItemRepository salesItemRepository) {
        this.salesItemRepository = salesItemRepository;
    }

    @Override
    public SalesItem save(SalesItem salesItem) {
        return salesItemRepository.save(salesItem);
    }

    @Override
    public List<SalesItem> saveAll(List<SalesItem> salesItems) {
        return salesItemRepository.saveAll(salesItems);
    }

    @Override
    public SalesItem findById(Long id) {
        return salesItemRepository.findById(id).orElseThrow(() -> new NotFoundException("SalesItem id " + id + " " + AppConstants.NOT_FOUND));
    }

    @Override
    public List<SalesItem> findBySalesId(Long salesId) {
        return salesItemRepository.findAllBySalesId(salesId);
    }

    @Override
    public void delete(Long id) {
        salesItemRepository.deleteById(id);
    }

    @Override
    public void deleteBySalesId(Long salesId) {
        List<Long> ids = findBySalesId(salesId).stream().map(SalesItem::getId).toList();
        salesItemRepository.deleteAllByIdInBatch(ids);
    }
}
