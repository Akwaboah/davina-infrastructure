package com.ikanetapps.hotelinfrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RecordNotFoundException extends RuntimeException {
    private final String message;

}
