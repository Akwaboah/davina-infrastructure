package com.ikanetapps.hotelinfrastructure.controller;

import com.ikanetapps.hotelinfrastructure.dto.request.RoomInputDto;
import com.ikanetapps.hotelinfrastructure.dto.request.RoomMapper;
import com.ikanetapps.hotelinfrastructure.dto.response.ApiResponse;
import com.ikanetapps.hotelinfrastructure.model.Room;
import com.ikanetapps.hotelinfrastructure.service.CloudflareR2Service;
import com.ikanetapps.hotelinfrastructure.service.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final CloudflareR2Service cloudflareR2Service;
    private final RoomMapper roomMapper;

    public RoomController(RoomService roomService, CloudflareR2Service cloudflareR2Service, RoomMapper roomMapper) {
        this.roomService = roomService;
        this.cloudflareR2Service = cloudflareR2Service;
        this.roomMapper = roomMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Room>> addRoom(@Valid @RequestBody RoomInputDto inputDto) {
        log.info("{}, Add room info", RoomController.class.getSimpleName());
        var response = new ApiResponse<>(
                HttpStatus.CREATED.value(), "Room added successfully",
                this.roomService.saveRoom(inputDto)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Room>>> listAllRooms() {
        log.info("{}, Fetch list of rooms info", RoomController.class.getSimpleName());
        var response = new ApiResponse<>(HttpStatus.OK.value(), "Fetched all rooms",
                this.roomService.listAllRooms()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Room>> findRoom(@PathVariable("roomId") Long roomId) {
        log.info("{}, Find room by id", RoomController.class.getSimpleName());
        var response = new ApiResponse<>(HttpStatus.OK.value(), "Room retrieved successfully", this.roomService.findById(roomId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Room>> updateRoom(@PathVariable("roomId") Long roomId, @Valid @RequestBody RoomInputDto inputDto) {
        log.info("{}, Update room", RoomController.class.getSimpleName());
        var roomRef = this.roomService.findById(roomId);
        var updateRoom = roomMapper.toEntity(inputDto);
        updateRoom.setId(roomRef.getId());
        var response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Room updated successfully",
                this.roomService.saveRoom(roomMapper.toDto(updateRoom))
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("upload-images")
    public ResponseEntity<ApiResponse<String>> uploadImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("banner") Boolean[] banner,
            @RequestParam("roomId") Long roomId) throws IOException {

        Room room = this.roomService.findById(roomId);

        HashMap<String, Boolean> processedFiles = new HashMap<>();
        for (int i = 0; i < files.length; i++) {
            String urlPath = this.cloudflareR2Service.uploadRoomImage(files[i], roomId);
            processedFiles.put(urlPath, banner[i]);
        }
        log.info("{} Processed Files", processedFiles);
        processedFiles.forEach((imageUrl, isBanner) -> this.roomService.saveImageUrl(room, imageUrl, isBanner));
        String filesUploadedMessage = processedFiles.size() + " files uploaded";
        var response = new ApiResponse<>(HttpStatus.CREATED.value(), "Files uploaded successfully", filesUploadedMessage);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteRoom(@RequestParam("roomId") Long roomId) {
        log.info("{}, Delete room by roomId", RoomController.class.getSimpleName());
        var response = new ApiResponse<>(HttpStatus.OK.value(), "Room deleted successfully",
                this.roomService.deleteRoom(roomId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
