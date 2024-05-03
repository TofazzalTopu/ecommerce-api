package com.info.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String sku;

    private Long quantity;

    private Double unitPrice;

    private Double totalPrice;


}
