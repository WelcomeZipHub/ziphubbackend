package com.ziphub.Configuration;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.ziphub.Entity.Photo;
import com.ziphub.Exception.ErrorCode;
import com.ziphub.Exception.PhotoException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Slf4j
public class FileStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStore.class);

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    public Photo storeFile(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
        }

        String storeFileName = createStoreFileName(file.getOriginalFilename());
        log.info("storeFileName: {}, getOriginalFileName: {}", storeFileName, file.getOriginalFilename());
        if(!checkFileExtension(storeFileName)) throw new IllegalStateException("The file is not a correct extension");

        // MetaData
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // AWS S3
        AmazonS3 s3Client = s3();
        String path = String.format("%s/%s", bucketName, storeFileName);
        String fileName = String.format("%s-%s", file.getName(), storeFileName);


        try {
            saveImageInAWS(s3Client, path, fileName, file.getInputStream(), Optional.of(metadata));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        String storageURL = "";
        Photo newPhoto = new Photo();
        newPhoto.setStoreFileName(storeFileName);
        newPhoto.setUploadFileName(file.getOriginalFilename());
        newPhoto.setStorage_url(storageURL);

        return newPhoto;
    }

    public void saveImageInAWS(AmazonS3 s3, String path, String fileName, InputStream inputStream, Optional<Map<String, String>> optionalMetadata) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to store file to s3", e);
        }
    }

    private String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    private boolean checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", ".jpg", ".pdf", ".doc", ".mp3"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    LOGGER.debug("Accepted file type : {}", extension);
                    return true;
                }
            }
        }
        return false;
    }
}
