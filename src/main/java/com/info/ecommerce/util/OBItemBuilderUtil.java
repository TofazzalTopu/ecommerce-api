package com.info.ecommerce.util;

import com.info.ecommerce.model.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBItemBuilderUtil {

    public static Item getItem() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Oil");
        item.setDescription("Oil");
        item.setSku("oil0123");
        item.setUnitPrice(20.0);
        return item;
    }

    public static List<Item> getItemList() {
        return new ArrayList<>(Arrays.asList(getItem(), new Item(2L, "Milk", "milk", "milk001", 20.0)));
    }
}
