package com.info.ecommerce.controller;

import com.info.ecommerce.model.Customer;
import com.info.ecommerce.repository.CustomerRepository;
import com.info.ecommerce.service.CustomerService;
import com.info.ecommerce.util.Converter;
import com.info.ecommerce.util.OBCustomerBuilderUtil;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest extends Converter {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private Customer customer;

    private List<Customer> customerList = new ArrayList<>();

    private static final String uri = "/customers";

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);
        customer = OBCustomerBuilderUtil.getCustomer();

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customerService.save(Mockito.any(Customer.class))).thenReturn(customer);
    }

    @Test
    @DisplayName("JUnit test to find all customers")
    public void findAll() throws Exception {
        customerList.addAll(OBCustomerBuilderUtil.getCustomerList());
        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        Mockito.when(customerService.findAll()).thenReturn(customerList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find customer by ID")
    public void findById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri + "/" + customer.getId()).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to save customer")
    public void save() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON).content(mapToJson(customer)).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to update customer")
    public void update() throws Exception {
        String name = "Butter";
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(customerService.findById(customer.getId())).thenReturn(customer);
        Customer existCustomer = customerService.findById(customer.getId());
        existCustomer.setName(name);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri + "/" + customer.getId()).accept(MediaType.APPLICATION_JSON).content(mapToJson(existCustomer)).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to delete customer")
    public void delete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri + "/" + customer.getId()).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

}

