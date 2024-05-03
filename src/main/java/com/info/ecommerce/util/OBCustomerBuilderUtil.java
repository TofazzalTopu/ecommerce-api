package com.info.ecommerce.util;

import com.info.ecommerce.model.Customer;

import java.util.Arrays;
import java.util.List;

public class OBCustomerBuilderUtil {

    public static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Jack");
        customer.setPhone("012345678912");
        return customer;
    }

    public static List<Customer> getCustomerList() {
        return Arrays.asList(getCustomer(), new Customer(2L, "Rana", "32435554"),
                new Customer(3L, "Tyen", "345344354"), new Customer(4L, "Peter", "32435554"));
    }
}
