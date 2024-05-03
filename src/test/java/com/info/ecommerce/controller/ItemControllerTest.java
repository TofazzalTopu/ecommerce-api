package com.info.ecommerce.controller;

import com.info.ecommerce.model.Item;
import com.info.ecommerce.repository.ItemRepository;
import com.info.ecommerce.service.ItemService;
import com.info.ecommerce.util.Converter;
import com.info.ecommerce.util.OBItemBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest extends Converter {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    ItemController itemController;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private Item item;

    private List<Item> itemList = new ArrayList<>();

    private static final String uri = "/items";

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
        item = OBItemBuilderUtil.getItem();
    }

    @Test
    @DisplayName("JUnit test to find all items")
    public void findAll() throws Exception {
        itemList.addAll(OBItemBuilderUtil.getItemList());
        Mockito.when(itemRepository.findAll()).thenReturn(itemList);
        Mockito.when(itemService.findAll()).thenReturn(itemList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find item by ID")
    public void findById() throws Exception {
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
        Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(item);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/" + item.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to save item")
    public void save() throws Exception {
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
        Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(item);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(item))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to update item")
    public void update() throws Exception {
        String name = "Solanki";
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
        Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(item);
        Mockito.when(itemRepository.findById(item.getId())).thenReturn(Optional.ofNullable(item));
        Mockito.when(itemService.findById(item.getId())).thenReturn(item);
        Item existItem = itemService.findById(item.getId());
        existItem.setName(name);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri + "/" + item.getId())
                .accept(MediaType.APPLICATION_JSON).content(mapToJson(existItem))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to delete item")
    public void delete() throws Exception {
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);
        Mockito.when(itemService.save(Mockito.any(Item.class))).thenReturn(item);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(uri + "/" + item.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}

