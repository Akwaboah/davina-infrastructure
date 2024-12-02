package com.ikanetapps.hotelinfrastructure.dto.response;

public record ApiResponse<T>(
        int status,
        String message,
        T data
) {
}
