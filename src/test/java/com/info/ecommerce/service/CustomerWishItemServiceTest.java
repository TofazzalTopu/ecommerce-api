package com.info.ecommerce.service;

import com.info.ecommerce.dto.CustomersWishItemResponseDTO;
import com.info.ecommerce.model.CustomersWishItem;
import com.info.ecommerce.repository.CustomersWishItemRepository;
import com.info.ecommerce.util.OBCustomerWishItemBuilderUtil;
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
public class CustomerWishItemServiceTest {

    @MockBean
    private CustomersWishItemRepository customersWishItemRepository;

    @Autowired
    private CustomersWishItemService customersWishItemService;

    public CustomerWishItemServiceTest() {
    }

    private CustomersWishItem customersWishItem;
    private CustomersWishItemResponseDTO customersWishItemResponseDTO;

    private List<CustomersWishItem> customersWishItemList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customersWishItem = OBCustomerWishItemBuilderUtil.getCustomerWishItem();
        customersWishItemList.addAll(OBCustomerWishItemBuilderUtil.getCustomersWishItemList());
        customersWishItemResponseDTO = OBCustomerWishItemBuilderUtil.getCustomersWishItemResponseDTO();
    }

    @Test
    @DisplayName("JUnit test for save CustomersWishItem")
    public void save() {
        Mockito.when(customersWishItemRepository.save(Mockito.any(CustomersWishItem.class)))
                .thenReturn(customersWishItem);
        CustomersWishItem savedCustomer = customersWishItemService.save(customersWishItem);

        assertNotNull(savedCustomer);
        assertEquals(1L, savedCustomer.getId().longValue());
        assertEquals(customersWishItem.getItem().getId(), savedCustomer.getItem().getId());
    }

    @Test
    @DisplayName("JUnit test to find CustomersWishItem by ID")
    public void findById() {
        Mockito.when(customersWishItemRepository.findById(customersWishItem.getId())).thenReturn(Optional.ofNullable(customersWishItem));
        CustomersWishItem savedCustomer = customersWishItemService.findById(customersWishItem.getId());
        assertNotNull(savedCustomer);
        assertEquals(customersWishItem.getId(), savedCustomer.getId());
    }

    @Test
    @DisplayName("JUnit test to find customer by ID")
    public void findAllByCustomerId() {
        Mockito.when(customersWishItemRepository.findAllByCustomerId(customersWishItem.getCustomer().getId())).thenReturn(customersWishItemList);
        CustomersWishItemResponseDTO wishItemResponseDTO = customersWishItemService.findAllByCustomerId(customersWishItem.getCustomer().getId());
        assertEquals(wishItemResponseDTO.getCustomer().getId(), customersWishItemResponseDTO.getCustomer().getId());
    }

}
