package com.info.ecommerce.controller;


import com.info.ecommerce.AppConstants;
import com.info.ecommerce.dto.Response;
import com.info.ecommerce.dto.SalesDTO;
import com.info.ecommerce.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@CrossOrigin
@RestController
@RequestMapping(value = "/sales")
public class SalesController {

    private SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping
    public ResponseEntity<Response> findAll() throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findAll()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody SalesDTO salesDTO) throws Exception {
        return ResponseEntity.created(new URI("")).body(new Response<>(HttpStatus.CREATED.value(),
                AppConstants.SAVED_SUCCESSFULLY, salesService.save(salesDTO)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @Valid @RequestBody SalesDTO salesDTO) throws Exception {
        return ResponseEntity.accepted().body(new Response<>(HttpStatus.ACCEPTED.value(),
                AppConstants.UPDATED_SUCCESSFULLY, salesService.update(id, salesDTO)));
    }

    @GetMapping(value = "/current-date-total-sales-amount")
    public ResponseEntity<Response> findCurrentDateSalesAmount() throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findCurrentDateSalesAmount()));
    }

    @GetMapping(value = "/max-sales-day")
    public ResponseEntity<Response> maxSalesDay(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findMaxSalesDateInGivenTimeRange(startDate, endDate)));
    }

    @GetMapping(value = "/amount-wise-top-five-items")
    public ResponseEntity<Response> findAmountWiseTop5SalesItems() throws Exception {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findTop5SalesItemsBasedOnTotalSalesAmount()));
    }

    @GetMapping(value = "/sales-wise-top-five-items")
    public ResponseEntity<Response> findLastMonthsTop5SalesItems() throws Exception {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        LocalDate start = previousMonth.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = previousMonth.with(TemporalAdjusters.lastDayOfMonth());
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.FETCH_SUCCESSFULLY, salesService.findTop5SalesItemsOfLastMonthBasedOnTotalNumberOfSales(start, end)));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        salesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
