package com.ikanetapps.hotelinfrastructure.controller;

import com.ikanetapps.hotelinfrastructure.dto.request.CustomerInputDto;
import com.ikanetapps.hotelinfrastructure.dto.response.ApiResponse;
import com.ikanetapps.hotelinfrastructure.model.Customer;
import com.ikanetapps.hotelinfrastructure.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customer")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService service) {
        this.customerService = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> addCustomer(@Valid @RequestBody CustomerInputDto customerDto) {
        log.info("{}, Add customer info", CustomerController.class.getSimpleName());
        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                this.customerService.saveCustomer(customerDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{phone}")
    public ResponseEntity<ApiResponse<Customer>> findByPhone(@PathVariable("phone") String phone) {
        log.info("{}, Retrieved customer by phone", CustomerController.class.getSimpleName());
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(),
                "Customer retrieved successfully", this.customerService.findCustomerByPhone(phone)), HttpStatus.OK);
    }
}

