package com.ikanetapps.hotelinfrastructure.controller;

import com.ikanetapps.hotelinfrastructure.service.CloudflareR2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/r2")
public class CloudflareR2Controller {

    private final CloudflareR2Service cloudflareR2Service;

    public CloudflareR2Controller(CloudflareR2Service cloudflareR2Service) {
        this.cloudflareR2Service = cloudflareR2Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        try {
            // Get the file URL after uploading it
            String fileUrl = cloudflareR2Service.uploadRoomImage(file, id);
            return ResponseEntity.ok("File uploaded successfully! URL: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }
}
