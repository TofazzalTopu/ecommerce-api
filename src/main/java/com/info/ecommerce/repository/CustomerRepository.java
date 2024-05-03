package com.info.ecommerce.repository;

import com.info.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByName(String name);

    Customer findByPhone(String phone);
}
