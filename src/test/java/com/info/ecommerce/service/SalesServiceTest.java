package com.info.ecommerce.service;

import com.info.ecommerce.model.Customer;
import com.info.ecommerce.repository.CustomerRepository;
import com.info.ecommerce.util.OBCustomerBuilderUtil;
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
public class SalesServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    public SalesServiceTest() {
    }

    private Customer customer;

    private List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customer = OBCustomerBuilderUtil.getCustomer();
        customerList.addAll(OBCustomerBuilderUtil.getCustomerList());
    }

    @Test
    @DisplayName("JUnit test for save customer")
    public void save() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = customerService.save(customer);

        assertNotNull(savedCustomer);
        assertEquals(1L, savedCustomer.getId().longValue());
        assertEquals(customer.getName(), savedCustomer.getName());
    }

    @Test
    @DisplayName("JUnit test for update customer")
    public void update() throws Exception {
        String name = "Solanki";
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));

        Customer exist = customerService.findById(customer.getId());
        exist.setName(name);
        Customer savedCustomer = customerService.save(exist);

        assertNotNull(savedCustomer);
        assertEquals(name, savedCustomer.getName());
    }

    @Test
    @DisplayName("JUnit test to find customer by ID")
    public void findById() {
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.ofNullable(customer));
        Customer savedCustomer = customerService.findById(customer.getId());
        assertNotNull(savedCustomer);
        assertEquals(customer.getId(), savedCustomer.getId());
    }

    @Test
    @DisplayName("JUnit test to find customer by name")
    public void findByName() {
        Mockito.when(customerRepository.findByName(customer.getName())).thenReturn(customer);
        Customer savedCustomer = customerService.findByName(customer.getName());

        assertNotNull(savedCustomer);
        assertEquals(customer.getName(), savedCustomer.getName());
    }

    @Test
    @DisplayName("JUnit test to find all customer")
    public void findAll() {
        Mockito.when(customerService.findAll()).thenReturn(customerList);
        List<Customer> customers = customerService.findAll();

        assertEquals(customers.size(), 4);
        assertEquals(customers, customerList);
    }
}
