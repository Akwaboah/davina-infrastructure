package com.ikanetapps.hotelinfrastructure.service;

import com.ikanetapps.hotelinfrastructure.dto.request.CustomerDto;
import com.ikanetapps.hotelinfrastructure.dto.request.CustomerMapper;
import com.ikanetapps.hotelinfrastructure.exception.CustomerPhoneNotFoundException;
import com.ikanetapps.hotelinfrastructure.model.Customer;
import com.ikanetapps.hotelinfrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.customerRepository = repository;
        this.customerMapper = mapper;
    }

    public Customer saveCustomer(CustomerDto dto) {
        var entity = customerMapper.toEntity(dto);
        return this.customerRepository.save(entity);
    }

    public Customer findCustomerByPhone(String phone) {
        return this.customerRepository.findByPhone(phone)
                .orElseThrow(() -> new CustomerPhoneNotFoundException("Customer with phone " + phone + " not found."));
    }

}
