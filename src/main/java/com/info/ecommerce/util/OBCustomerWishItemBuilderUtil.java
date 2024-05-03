package com.info.ecommerce.util;

import com.info.ecommerce.dto.CustomersWishItemResponseDTO;
import com.info.ecommerce.dto.WishItemDTO;
import com.info.ecommerce.model.CustomersWishItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBCustomerWishItemBuilderUtil {

    public static CustomersWishItem getCustomerWishItem() {
        CustomersWishItem customersWishItem = new CustomersWishItem();
        customersWishItem.setId(1L);
        customersWishItem.setCustomer(OBCustomerBuilderUtil.getCustomer());
        customersWishItem.setItem(OBItemBuilderUtil.getItem());
        return customersWishItem;
    }

    public static List<CustomersWishItem> getCustomersWishItemList() {
        return Arrays.asList(getCustomerWishItem());
    }

    public static CustomersWishItemResponseDTO getCustomersWishItemResponseDTO(){
        CustomersWishItemResponseDTO customersWishItemResponseDTO = new CustomersWishItemResponseDTO();
        customersWishItemResponseDTO.setCustomer(OBCustomerBuilderUtil.getCustomer());

        WishItemDTO wishItemDTO = new WishItemDTO();
        wishItemDTO.setWishId(1L);
        wishItemDTO.setItem(OBItemBuilderUtil.getItem());

        List<WishItemDTO> wishItemDTOList = new ArrayList<>();
        wishItemDTOList.add(wishItemDTO);
        customersWishItemResponseDTO.setWishItems(wishItemDTOList);
        return customersWishItemResponseDTO;
    }
}
