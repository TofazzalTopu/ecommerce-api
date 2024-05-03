package com.info.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

import java.util.List;

@ToString
public class SalesDTO {

    @NotNull
    private Long customerId;

    @NotNull
    @NotEmpty
    private List<ItemRequestDTO> itemDTOS;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ItemRequestDTO> getItemDTOS() {
        return itemDTOS;
    }

    public void setItemDTOS(List<ItemRequestDTO> itemDTOS) {
        this.itemDTOS = itemDTOS;
    }
}
