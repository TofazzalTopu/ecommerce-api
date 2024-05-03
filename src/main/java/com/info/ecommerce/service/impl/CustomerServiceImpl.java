package com.info.ecommerce.service.impl;

import com.info.ecommerce.AppConstants;
import com.info.ecommerce.exception.AlreadyExistException;
import com.info.ecommerce.exception.NotFoundException;
import com.info.ecommerce.model.Customer;
import com.info.ecommerce.repository.CustomerRepository;
import com.info.ecommerce.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll(Sort.by("id").descending().and(Sort.by("name").ascending()));
    }

    @Override
    public Customer save(Customer customer) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("customer: {}", customer);
        }
        validateSave(customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("id: {} customer: {}", id, customer);
        }
        Customer customerById = findById(id);
        validateUpdate(customer, customerById.getId());
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer id " + id + " " + AppConstants.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        if (log.isDebugEnabled()) {
            log.debug("id: {} ", id);
        }
        findById(id);
        customerRepository.deleteById(id);
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public Customer findByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public void validateSave(Customer customer) {
        if (Objects.nonNull(findByName(customer.getName()))) {
            log.error(AppConstants.CUSTOMER_NAME_ALREADY_EXITS);
            throw new AlreadyExistException(AppConstants.CUSTOMER_NAME_ALREADY_EXITS);
        }
        if (Objects.nonNull(findByPhone(customer.getPhone()))) {
            log.error(AppConstants.CUSTOMER_PHONE_ALREADY_EXITS);
            throw new AlreadyExistException(AppConstants.CUSTOMER_PHONE_ALREADY_EXITS);
        }
    }

    public void validateUpdate(Customer customer, Long id) {
        Customer CustomerByName = findByName(customer.getName());
        Customer CustomerByPhone = findByPhone(customer.getPhone());
        if (Objects.nonNull(CustomerByName) && !CustomerByName.getId().equals(id)) {
            log.error(AppConstants.CUSTOMER_NAME_ALREADY_EXITS);
            throw new AlreadyExistException(AppConstants.CUSTOMER_NAME_ALREADY_EXITS);
        }
        if (Objects.nonNull(CustomerByPhone) && !CustomerByPhone.getId().equals(id)) {
            log.error(AppConstants.CUSTOMER_PHONE_ALREADY_EXITS);
            throw new AlreadyExistException(AppConstants.CUSTOMER_PHONE_ALREADY_EXITS);
        }
    }
}
