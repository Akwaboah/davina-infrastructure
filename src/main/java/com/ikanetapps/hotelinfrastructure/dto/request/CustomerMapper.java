package com.ikanetapps.hotelinfrastructure.dto.request;

import com.ikanetapps.hotelinfrastructure.model.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toEntity(@NotNull final CustomerInputDto inputDto) {
        if (inputDto == null) {
            throw new NullPointerException("CustomerDto cannot be null");
        }
        Customer customer = new Customer();
        customer.setId(inputDto.id());
        customer.setFirstName(inputDto.firstName());
        customer.setLastName(inputDto.lastName());
        customer.setPhone(inputDto.phone());
        customer.setEmail(inputDto.email());
        return customer;
    }


}
