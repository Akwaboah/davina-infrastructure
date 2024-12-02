package com.ikanetapps.hotelinfrastructure.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "cloudflare.r2")
@Validated
@Setter
@Getter
public class CloudflareR2Config {

    @NotBlank(message = "Access key must not be blank")
    private String accessKey;

    @NotBlank(message = "Secret key must not be blank")
    private String secretKey;

    @NotBlank(message = "Bucket name must not be blank")
    private String bucketName;

    @NotBlank(message = "Region must not be blank")
    private String region;

    @NotBlank(message = "Endpoint must not be blank")
    private String endpoint;

    @NotBlank(message = "Storage directory must not be blank")
    private String storageDirectory;

    @NotBlank(message = "Public access must not be blank")
    private String publicAccess;

    /**
     * Configures an S3Client for interacting with Cloudflare R2.
     *
     * @return Configured S3Client instance.
     */
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of(region))  // Specify region
                .endpointOverride(URI.create(endpoint + "/" + storageDirectory)) // R2 endpoint
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
