package com.info.ecommerce.controller;


import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.Response;
import com.info.ecommerce.model.Customer;
import com.info.ecommerce.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Response> findAll() throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, customerService.findAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, customerService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody Customer customer) throws Exception {
        return ResponseEntity.created(new URI("")).body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.SAVED_SUCCESSFULLY, customerService.save(customer)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody Customer customer) throws Exception {
        return ResponseEntity.accepted().body(new Response<>(HttpStatus.ACCEPTED.value(),
                AppConstants.UPDATED_SUCCESSFULLY, customerService.update(id, customer)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
