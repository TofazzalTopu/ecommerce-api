package com.info.ecommerce.dto;

import com.info.ecommerce.model.Item;
import lombok.Data;

@Data
public class WishItemDTO {
    private Long wishId;
    private Item item;


}
