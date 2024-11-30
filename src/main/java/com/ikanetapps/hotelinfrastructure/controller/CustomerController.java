package com.ikanetapps.hotelinfrastructure.controller;

import com.ikanetapps.hotelinfrastructure.dto.request.CustomerDto;
import com.ikanetapps.hotelinfrastructure.dto.response.ApiResponse;
import com.ikanetapps.hotelinfrastructure.model.Customer;
import com.ikanetapps.hotelinfrastructure.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService service) {
        this.customerService = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        log.info("{}, Add customer info", CustomerController.class.getSimpleName());
        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                this.customerService.saveCustomer(customerDto),
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{phone}")
    public ResponseEntity<ApiResponse<Customer>> findByPhone(@PathVariable("phone") String phone) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(),
                "", this.customerService.findCustomerByPhone(phone), LocalDateTime.now()), HttpStatus.OK);
    }
}

