package com.ikanetapps.hotelinfrastructure.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        int statusCode,
        String error,
        String message,
        LocalDateTime timestamp
) {
}
