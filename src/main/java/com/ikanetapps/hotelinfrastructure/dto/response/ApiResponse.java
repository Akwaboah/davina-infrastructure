package com.ikanetapps.hotelinfrastructure.dto.response;

import java.time.LocalDateTime;


public record ApiResponse<T>(
        int statusCode,
        String message,
        T data,
        LocalDateTime timestamp
) {
}
