package com.info.ecommerce.service.impl;

import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.CustomersWishItemResponseDTO;
import com.info.ecommerce.dto.WishItemDTO;
import com.info.ecommerce.exception.AlreadyExistException;
import com.info.ecommerce.exception.NotFoundException;
import com.info.ecommerce.model.Customer;
import com.info.ecommerce.model.CustomersWishItem;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.repository.CustomersWishItemRepository;
import com.info.ecommerce.service.CustomerService;
import com.info.ecommerce.service.CustomersWishItemService;
import com.info.ecommerce.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomersWishItemServiceImpl implements CustomersWishItemService {

    private CustomersWishItemRepository customersWishItemRepository;
    private CustomerService customerService;
    private ItemService itemService;

    public CustomersWishItemServiceImpl(CustomersWishItemRepository customersWishItemRepository, CustomerService customerService, ItemService itemService) {
        this.customersWishItemRepository = customersWishItemRepository;
        this.customerService = customerService;
        this.itemService = itemService;
    }

    @Override
    public CustomersWishItem findById(Long id) {
        return customersWishItemRepository.findById(id).orElseThrow(() -> new NotFoundException("CustomersWishItem id "
                + id + " " + AppConstants.NOT_FOUND));
    }

    @Override
    public CustomersWishItemResponseDTO findAllByCustomerId(Long customerId) {
        List<CustomersWishItem> wishItems = customersWishItemRepository.findAllByCustomerId(customerId);
        return mapTOCustomersWishItemResponseDTO(wishItems);
    }

    @Override
    public CustomersWishItem save(CustomersWishItem customersWishItem) {
        validate(customersWishItem);
        Customer customer = customerService.findById(customersWishItem.getCustomer().getId());
        Item item = itemService.findById(customersWishItem.getItem().getId());
        customersWishItem.setCustomer(customer);
        customersWishItem.setItem(item);
        return customersWishItemRepository.save(customersWishItem);
    }

    @Override
    public void delete(Long id) {
        customersWishItemRepository.deleteById(id);
    }

    private void validate(CustomersWishItem item) {
        Optional<CustomersWishItem> customersWishItem = customersWishItemRepository.findByCustomerIdAndItemId(item.getCustomer().getId(), item.getItem().getId());
        if (customersWishItem.isPresent()) {
            log.error(AppConstants.ITEM_IS_ALREADY_EXITS_FOR_THIS_CUSTOMER + ", itemId: {} customerId: {} ", item.getItem().getId(), item.getCustomer().getId());
            throw new AlreadyExistException(AppConstants.ITEM_IS_ALREADY_EXITS_FOR_THIS_CUSTOMER);
        }
    }

    private CustomersWishItemResponseDTO mapTOCustomersWishItemResponseDTO(List<CustomersWishItem> wishItemList) {
        CustomersWishItemResponseDTO customersWishItemResponseDTO = new CustomersWishItemResponseDTO();
        if (!wishItemList.isEmpty()) {
            customersWishItemResponseDTO.setCustomer(wishItemList.get(0).getCustomer());

            List<WishItemDTO> wishItemDTOList = new ArrayList<>();
            for (CustomersWishItem wishItem : wishItemList) {
                WishItemDTO wishItemDTO = new WishItemDTO();
                wishItemDTO.setWishId(wishItem.getId());
                wishItemDTO.setItem(wishItem.getItem());
                wishItemDTOList.add(wishItemDTO);
            }
            customersWishItemResponseDTO.setWishItems(wishItemDTOList);
        }
        return customersWishItemResponseDTO;
    }

}
