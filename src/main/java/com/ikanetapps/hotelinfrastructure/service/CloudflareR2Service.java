package com.ikanetapps.hotelinfrastructure.service;

import com.ikanetapps.hotelinfrastructure.config.CloudflareR2Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
@Slf4j
public class CloudflareR2Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final String storageDirectory;
    private final String publicAccess;

    public CloudflareR2Service(CloudflareR2Config cloudflareR2Config) {
        this.s3Client = cloudflareR2Config.s3Client();
        this.bucketName = cloudflareR2Config.getBucketName();
        this.storageDirectory = cloudflareR2Config.getStorageDirectory();
        this.publicAccess = cloudflareR2Config.getPublicAccess();
    }

    public String uploadRoomImage(MultipartFile file, Long roomId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("File must have a name");
        }

        String directory = "room-images/" + roomId + "/";
        String fileName = file.getOriginalFilename(); // Preserve the original file name
        String fullPath = directory + fileName; // Full path to the file

        try (InputStream fileInputStream = file.getInputStream()) {

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullPath)
                    .contentDisposition("attachment; filename=\"" + fileName + "\"") // Force original name
                    .contentType(file.getContentType()) // Preserve the content type
                    .metadata(Map.of("Content-Disposition", "attachment; filename=\"" + fileName + "\"")) // Set metadata
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(fileInputStream, file.getSize());

            PutObjectResponse response = s3Client.putObject(putObjectRequest, requestBody);
            log.info("{}, File uploaded info {}", CloudflareR2Service.class.getSimpleName(), response.eTag());

            return String.format("%s/%s/%s", publicAccess, storageDirectory, fullPath);
        } catch (Exception e) {
            throw new IOException("Failed to upload file", e);
        }
    }
}
