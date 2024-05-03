package com.info.ecommerce.controller;

import com.info.ecommerce.model.Customer;
import com.info.ecommerce.model.CustomersWishItem;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.repository.CustomersWishItemRepository;
import com.info.ecommerce.service.CustomersWishItemService;
import com.info.ecommerce.util.Converter;
import com.info.ecommerce.util.OBCustomerBuilderUtil;
import com.info.ecommerce.util.OBCustomerWishItemBuilderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerWishItemControllerTest extends Converter {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomersWishItemService customersWishItemService;

    @MockBean
    private CustomersWishItemRepository customersWishItemRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private Customer customer;
    private CustomersWishItem customersWishItem;

    private List<Customer> customerList = new ArrayList<>();

    private static final String uri = "/wish/items";

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
        customer = OBCustomerBuilderUtil.getCustomer();
        customersWishItem = OBCustomerWishItemBuilderUtil.getCustomerWishItem();

        Mockito.when(customersWishItemRepository.save(Mockito.any(CustomersWishItem.class))).thenReturn(customersWishItem);
        Mockito.when(customersWishItemService.save(Mockito.any(CustomersWishItem.class))).thenReturn(customersWishItem);
    }

    @Test
    @DisplayName("JUnit test to find CustomersWishItem by ID")
    public void findById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to findAllByCustomerId by ID")
    public void findAllByCustomerId() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/customer/1" + customer.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to save CustomersWishItem")
    public void save() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(customersWishItem))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }


    @Test
    @DisplayName("JUnit test to delete CustomersWishItem")
    public void delete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(uri + "/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}

