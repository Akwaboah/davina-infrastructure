package com.ikanetapps.hotelinfrastructure.dto.request;

import com.ikanetapps.hotelinfrastructure.model.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toEntity(@NotNull final CustomerDto customerDto) {
        if (customerDto == null) {
            throw new NullPointerException("CustomerDto cannot be null");
        }
        var customer = new Customer();
        customer.setFirstName(customerDto.firstName());
        customer.setLastName(customerDto.lastName());
        customer.setPhone(customerDto.phone());
        customer.setEmail(customerDto.email());
        return customer;
    }

    public CustomerDto toDto(@NotNull final Customer customer) {
        if (customer == null) {
            throw new NullPointerException("Customer entity cannot be null");
        }
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhone(),
                customer.getEmail()
        );
    }
}
