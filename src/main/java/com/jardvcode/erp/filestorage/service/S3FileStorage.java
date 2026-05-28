package com.jardvcode.erp.filestorage.service;

import com.jardvcode.erp.filestorage.configuration.S3Properties;
import com.jardvcode.erp.filestorage.exception.FileStorageUploadException;
import com.jardvcode.erp.filestorage.exception.TemporaryInvoiceUrlGenerationException;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.providers.AwsRegionProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URI;
import java.time.Duration;

@Service
public final class S3FileStorage {

    private final static Logger LOG = LoggerFactory.getLogger(S3FileStorage.class);

    private final S3Template s3Template;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final S3Properties s3Properties;
    private final AwsRegionProvider awsRegionProvider;

    public S3FileStorage(S3Template s3Template, S3Client s3Client, S3Presigner s3Presigner, S3Properties s3Properties, AwsRegionProvider awsRegionProvider) {
        this.s3Template = s3Template;
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.s3Properties = s3Properties;
        this.awsRegionProvider = awsRegionProvider;
    }

    public String upload(byte[] data, String filename, String path) throws FileStorageUploadException {
        try {
            ByteArrayResource byteArrayResource = new ByteArrayResource(data);

            ObjectMetadata metadata = ObjectMetadata.builder()
                    .contentType("application/pdf")
                    .contentDisposition("attachment; filename=\"" + filename + "\"")
                    .build();

            String bucketName = s3Properties.getBucketName();

            s3Template.upload(bucketName, path, byteArrayResource.getInputStream(), metadata);

            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(path)
                    .build();

            Region region = awsRegionProvider.getRegion();

            return S3Utilities.builder().region(region).build().getUrl(getUrlRequest).toString();
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
            throw new FileStorageUploadException(filename);
        }
    }

    public String generateTemporaryUrl(String url) {
        try {
            URI uri = new URI(url);
            String path = uri.getPath().substring(1);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3Properties.getBucketName())
                    .key(path)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return s3Presigner.presignGetObject(presignRequest).url().toString();

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new TemporaryInvoiceUrlGenerationException();
        }
    }

}
