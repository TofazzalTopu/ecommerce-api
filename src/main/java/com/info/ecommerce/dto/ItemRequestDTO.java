package com.info.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class ItemRequestDTO {

    @NotNull
    private Long itemId;

    private int quantity;

    public ItemRequestDTO() {
    }

    public ItemRequestDTO(Long itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
