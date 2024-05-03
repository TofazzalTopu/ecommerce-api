package com.info.ecommerce.service;

import com.info.ecommerce.model.Customer;

import java.util.List;

public interface CustomerService {


    List<Customer> findAll();

    Customer save(Customer customer) throws Exception;

    Customer update(Long id, Customer customer) throws Exception;

    Customer findById(Long id);

    void delete(Long id);

    Customer findByName(String name);

    Customer findByPhone(String sku);
}
