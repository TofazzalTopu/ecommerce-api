package com.info.ecommerce.dto;

import com.info.ecommerce.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CustomersWishItemResponseDTO {

    private Customer customer;

    private List<WishItemDTO> wishItems;


}
