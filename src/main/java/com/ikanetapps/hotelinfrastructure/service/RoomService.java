package com.ikanetapps.hotelinfrastructure.service;

import com.ikanetapps.hotelinfrastructure.dto.request.RoomInputDto;
import com.ikanetapps.hotelinfrastructure.dto.request.RoomMapper;
import com.ikanetapps.hotelinfrastructure.exception.RecordNotFoundException;
import com.ikanetapps.hotelinfrastructure.model.Room;
import com.ikanetapps.hotelinfrastructure.repository.RoomImagesRepository;
import com.ikanetapps.hotelinfrastructure.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomImagesRepository roomImagesRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomImagesRepository roomImagesRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomImagesRepository = roomImagesRepository;
        this.roomMapper = roomMapper;
    }

    public Room saveRoom(RoomInputDto inputDto) {
        var entity = roomMapper.toEntity(inputDto);
        return roomRepository.save(entity);
    }

    public List<Room> listAllRooms() {
        return this.roomRepository.findAll();
    }

    public Room findById(Long roomId) {
        return this.roomRepository.findById(roomId).orElseThrow(() -> new RecordNotFoundException("Room with id " + roomId + " not found."));
    }

    public Long deleteRoom(Long roomId) {
        this.roomRepository.deleteById(roomId);
        return roomId;
    }

    public void saveImageUrl(Room room, String imageUrl, Boolean banner) {
        this.roomImagesRepository.save(roomMapper.toRoomImagesEntity(room, imageUrl, banner));
    }

}
