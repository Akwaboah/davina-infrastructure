package com.ikanetapps.hotelinfrastructure.dto.request;

import com.ikanetapps.hotelinfrastructure.model.Room;
import com.ikanetapps.hotelinfrastructure.model.RoomImages;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class RoomMapper {
    public Room toEntity(@NotNull RoomInputDto inputDto) {
        if (inputDto == null) {
            throw new NullPointerException("RoomDto cannot be null");
        }
        Room room = new Room();
        room.setId(inputDto.id());
        room.setRoomNumber(inputDto.roomNumber());
        room.setRoomType(inputDto.roomType());
        room.setLocation(inputDto.location());
        room.setName(inputDto.name());
        room.setDescription(inputDto.description());
        room.setBedCount(inputDto.bedCount());
        room.setMaxOccupancy(inputDto.maxOccupancy());
        room.setBasePrice(inputDto.basePrice());
        room.setAvailability(inputDto.availability());
        room.setRating(inputDto.rating());
        return room;
    }

    public RoomInputDto toDto(Room room) {
        if (room == null) {
            throw new NullPointerException("Room cannot be null");
        }
        return new RoomInputDto(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getLocation(),
                room.getName(),
                room.getDescription(),
                room.getBedCount(),
                room.getMaxOccupancy(),
                room.getBasePrice(),
                room.getAvailability(),
                room.getRating()
        );
    }

    public RoomImages toRoomImagesEntity(Room room, String imageUrl, Boolean banner) {
        RoomImages roomImages = new RoomImages();
        roomImages.setRoom(room);
        roomImages.setImageUrl(imageUrl);
        roomImages.setBanner(banner);
        return roomImages;
    }
}
