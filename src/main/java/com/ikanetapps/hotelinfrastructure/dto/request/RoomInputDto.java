package com.ikanetapps.hotelinfrastructure.dto.request;

import com.ikanetapps.hotelinfrastructure.model.Room;
import com.ikanetapps.hotelinfrastructure.utils.Locations;
import com.ikanetapps.hotelinfrastructure.utils.RoomTypes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Room}
 */
public record RoomInputDto(Long id,
                           @NotBlank(message = "Room number can't be empty") String roomNumber,
                           @NotNull(message = "Room type can't be empty") RoomTypes roomType,
                           @NotNull(message = "Room location can't be empty") Locations location, String name,
                           String description,
                           @Min(message = "Bed count must be greater than or equal to 1", value = 1) int bedCount,
                           @Min(message = "Max occupancy must be greater than or equal to 1", value = 1) int maxOccupancy,
                           @NotNull @Min(0) Double basePrice, Boolean availability,
                           @NotNull @Min(0) Double rating) implements Serializable {
}