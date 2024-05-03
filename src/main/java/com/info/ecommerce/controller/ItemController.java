package com.info.ecommerce.controller;


import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.Response;
import com.info.ecommerce.model.Item;
import com.info.ecommerce.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
@RequestMapping(value = "/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Response> findAll() throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, itemService.findAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, itemService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody Item item) throws URISyntaxException {
        return ResponseEntity.created(new URI("")).body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.SAVED_SUCCESSFULLY, itemService.save(item)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Item item) throws Exception {
        return ResponseEntity.accepted().body(new Response<>(HttpStatus.ACCEPTED.value(),
                AppConstants.UPDATED_SUCCESSFULLY, itemService.update(id, item)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
