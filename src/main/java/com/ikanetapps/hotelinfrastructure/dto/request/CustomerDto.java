package com.ikanetapps.hotelinfrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.ikanetapps.hotelinfrastructure.model.Customer}
 */
public record CustomerDto(
        Long id,
        @NotBlank(message = "Firstname must not be empty") String firstName,
        @NotBlank(message = "Lastname must not be empty") String lastName,
        @NotBlank(message = "Phone number must not be empty") String phone,
        String email) implements Serializable {
}