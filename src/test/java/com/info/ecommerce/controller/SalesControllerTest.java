package com.info.ecommerce.controller;

import com.info.ecommerce.dto.ItemRequestDTO;
import com.info.ecommerce.dto.SalesDTO;
import com.info.ecommerce.model.Customer;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.model.Sales;
import com.info.ecommerce.model.SalesItem;
import com.info.ecommerce.repository.SalesRepository;
import com.info.ecommerce.service.SalesService;
import com.info.ecommerce.util.Converter;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalesControllerTest extends Converter {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private SalesController salesController;

    @MockBean
    private SalesService salesService;

    @MockBean
    private SalesRepository salesRepository;

    @Autowired
    WebApplicationContext webApplicationContext;

    private SalesItem salesItem;
    private SalesDTO salesDTO;
    private Item item;
    private Customer customer;
    private Sales sales;
    private List<Sales> salesList = new ArrayList<>();
    private List<SalesItem> salesItemList = new ArrayList<>();

    private List<ItemRequestDTO> itemRequestDTOS = new ArrayList<>();

    private static final String uri = "/sales";

    @BeforeEach
    public void setup() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.openMocks(this);

        item = new Item();
        item.setId(1L);
        item.setName("Oil");
        item.setDescription("Oil");
        item.setSku("oil0123");
        item.setUnitPrice(20.0);

        customer = new Customer();
        customer.setName("Rana");
        itemRequestDTOS.addAll(Arrays.asList(new ItemRequestDTO(1L, 10), new ItemRequestDTO(2L, 15),
                new ItemRequestDTO(3L, 20), new ItemRequestDTO(4L, 25),
                new ItemRequestDTO(5L, 20), new ItemRequestDTO(6L, 10)));
        salesDTO = new SalesDTO();
        salesDTO.setCustomerId(1L);
        salesDTO.setItemDTOS(itemRequestDTOS);

        sales = new Sales();
        sales.setId(1L);
        sales.setCustomer(customer);

        salesItem = new SalesItem();
        salesItem.setId(1L);
        salesItem.setItem(item);
        salesItem.setSales(sales);
        salesItem.setQuantity(3);
        salesItem.setTotalPrice(item.getUnitPrice() * salesItem.getQuantity());
        salesItemList.add(salesItem);

        sales.setSalesItems(salesItemList);
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        Mockito.when(salesService.save(Mockito.any(SalesDTO.class))).thenReturn(sales);
    }

    @Test
    @DisplayName("JUnit test to find all sales")
    public void findAll() throws Exception {
        Mockito.when(salesRepository.findAll()).thenReturn(salesList);
        Mockito.when(salesService.findAll()).thenReturn(salesList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find sales by ID")
    public void findById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri + "/" + sales.getId()).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to save sales")
    public void save() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(uri)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(salesDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to update sales")
    public void update() throws Exception {
        sales.setSalesItems(salesItemList);
        salesDTO.setCustomerId(2L);
        Mockito.when(salesRepository.save(Mockito.any(Sales.class))).thenReturn(sales);
        Mockito.when(salesService.save(Mockito.any(SalesDTO.class))).thenReturn(sales);
        Mockito.when(salesRepository.findById(sales.getId())).thenReturn(Optional.ofNullable(sales));
        Mockito.when(salesService.findById(sales.getId())).thenReturn(sales);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(uri + "/" + sales.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(mapToJson(salesDTO))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find current date sales amount")
    public void findCurrentDateSalesAmount() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/todays-total-sales")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find maximum sales date")
    public void findMaxSalesDateInGivenTimeRange() throws Exception {
        sales.setSalesItems(salesItemList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/max-sales-day?startDate=2024-04-10&endDate=2024-05-10")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to find last months top five sales items")
    public void findLastMonthsTop5SalesItems() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(uri + "/sales-wise-top-five-items")
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("JUnit test to delete sales")
    public void delete() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(uri + "/" + sales.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }


}

