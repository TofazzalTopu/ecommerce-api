package com.info.ecommerce.controller;


import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.Response;
import com.info.ecommerce.model.CustomersWishItem;
import com.info.ecommerce.service.CustomersWishItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping(value = "/wish/items")
public class CustomersWishItemController {

    private CustomersWishItemService customersWishItemService;

    public CustomersWishItemController(CustomersWishItemService customersWishItemService) {
        this.customersWishItemService = customersWishItemService;
    }

    @GetMapping(value = "/customer/{customerId}")
    public ResponseEntity<Response> findAllByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, customersWishItemService.findAllByCustomerId(customerId)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, customersWishItemService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody CustomersWishItem customersWishItem) throws Exception {
        return ResponseEntity.created(new URI("")).body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.SAVED_SUCCESSFULLY, customersWishItemService.save(customersWishItem)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customersWishItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
