package com.erp.filestorage.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "app.s3")
public class S3Properties {

    @NotBlank(message = "The S3 bucket name must not be blank or null")
    private String bucketName;

    @NotBlank(message = "The S3 region must not be blank or null")
    private String region;

    @Positive(message = "The URL expiration time must be a positive number")
    private int urlExpirationMinutes = 15;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public int getUrlExpirationMinutes() {
        return urlExpirationMinutes;
    }

    public void setUrlExpirationMinutes(int urlExpirationMinutes) {
        this.urlExpirationMinutes = urlExpirationMinutes;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}