package com.info.ecommerce.service;

import com.info.ecommerce.model.Item;
import com.info.ecommerce.repository.ItemRepository;
import com.info.ecommerce.util.OBItemBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    public ItemServiceTest() {
    }

    private Item item;

    private List<Item> itemList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        item = OBItemBuilderUtil.getItem();
        itemList.addAll(OBItemBuilderUtil.getItemList());
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
    }

    @Test
    @DisplayName("JUnit test for save item")
    public void save() {
        Item savedItem = itemService.save(item);

        assertNotNull(savedItem);
        assertEquals(1L, savedItem.getId().longValue());
        assertEquals(item.getName(), savedItem.getName());
    }

    @Test
    @DisplayName("JUnit test for update item")
    public void update() {
        String name = "Milk";
        Mockito.when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));

        Item exist = itemService.findById(item.getId());
        exist.setName(name);
        Item savedItem = itemService.save(exist);

        assertNotNull(savedItem);
        assertEquals(name, savedItem.getName());
    }

    @Test
    @DisplayName("JUnit test to find item by ID")
    public void findById() {
        Mockito.when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));
        Item savedItem = itemService.findById(item.getId());
        assertNotNull(savedItem);
        assertEquals(item.getId(), savedItem.getId());
    }

    @Test
    @DisplayName("JUnit test to find item by name")
    public void findByName() {
        Mockito.when(itemRepository.findByName(item.getName())).thenReturn(item);
        Item savedItem = itemService.findByName(item.getName());

        assertNotNull(savedItem);
        assertEquals(item.getName(), savedItem.getName());
    }

    @Test
    @DisplayName("JUnit test to find all item")
    public void findAll() throws Exception {
        Mockito.when(itemService.findAll()).thenReturn(itemList);
        List<Item> items = itemService.findAll();

        assertEquals(2, items.size());
        assertEquals(items, itemList);
    }
}
