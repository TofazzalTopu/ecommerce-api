package com.info.ecommerce.service;

import com.info.ecommerce.dto.CustomersWishItemResponseDTO;
import com.info.ecommerce.model.CustomersWishItem;

public interface CustomersWishItemService {

    CustomersWishItem findById(Long id);

    CustomersWishItemResponseDTO findAllByCustomerId(Long customerId);

    CustomersWishItem save(CustomersWishItem item);

    void delete(Long id);


}
