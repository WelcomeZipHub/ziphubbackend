package com.ziphub.Configuration;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.ziphub.Entity.Photo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;


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

    public Photo storeFile(MultipartFile file, String uniqueId) {
        if(file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
        }

        String storeFileName = createStoreFileName(file.getOriginalFilename());
        if(!checkFileExtension(storeFileName)) throw new IllegalStateException("The file is not a correct extension");

        // MetaData
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        // AWS S3
        AmazonS3 s3Client = s3();
        String path = String.format("%s/%s", bucketName, uniqueId);

        try {
            saveImageInAWS(s3Client, path, storeFileName, file.getInputStream(), Optional.of(metadata));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        Photo newPhoto = new Photo();
        newPhoto.setStoreFileName(storeFileName);
        newPhoto.setUploadFileName(file.getOriginalFilename());
        newPhoto.setStorage_url(path);

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

    public byte[] download(String path, String storeFileName) {
        AmazonS3 s3Client = s3();

        try {
            S3Object object = s3Client.getObject(path, storeFileName);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
