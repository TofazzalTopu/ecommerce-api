package com.info.ecommerce.service.impl;

import com.info.ecommerce.AppConstants;
import com.info.ecommerce.exception.AlreadyExistException;
import com.info.ecommerce.exception.NotFoundException;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.repository.ItemRepository;
import com.info.ecommerce.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll(Sort.by("id").descending().and(Sort.by("name").ascending()));
    }

    @Override
    public Item save(Item item) {
        if (log.isDebugEnabled()) {
            log.debug("item: {} ", item);
        }
        validateItem(item);
        return itemRepository.save(item);
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item id " + id + " " + AppConstants.NOT_FOUND));
    }

    @Override
    public List<Item> findByIds(List<Long> ids) {
        return itemRepository.findAllByIdIn(ids);
    }

    @Override
    public Item update(Long id, Item item) {
        if (log.isDebugEnabled()) {
            log.debug("id: {}, item: {} ", id, item);
        }
        findById(id);
        item.setId(id);
        return itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        itemRepository.deleteById(id);
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public Item findBySku(String sku) {
        return itemRepository.findBySku(sku);
    }

    public void validateItem(Item item) {
        if (Objects.nonNull(findByName(item.getName()))) {
            log.error(AppConstants.ITEM_NAME_ALREADY_EXITS + ", name: {} ", item.getName());
            throw new AlreadyExistException(AppConstants.ITEM_NAME_ALREADY_EXITS);
        }
        if (Objects.nonNull(findBySku(item.getSku()))) {
            log.error(AppConstants.ITEM_SKU_ALREADY_EXITS + ", sku: {} ", item.getSku());
            throw new AlreadyExistException(AppConstants.ITEM_SKU_ALREADY_EXITS);
        }
    }
}
