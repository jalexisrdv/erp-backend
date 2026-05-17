package com.erp.filestorage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Configuration {

    private final S3Properties s3Properties;

    public S3Configuration(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(s3Properties.getRegion()))
                .build();
    }

}
