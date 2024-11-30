package com.ikanetapps.hotelinfrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CustomerPhoneNotFoundException extends RuntimeException {
    private final String message;

}
