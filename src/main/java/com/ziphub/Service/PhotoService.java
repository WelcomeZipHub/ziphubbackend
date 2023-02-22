package com.ziphub.Service;

import com.ziphub.Dto.PhotoDto;
import com.ziphub.Entity.Photo;
import com.ziphub.Exception.ErrorCode;
import com.ziphub.Exception.PhotoException;
import com.ziphub.Repository.PhotoRepository;
import com.ziphub.Configuration.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final FileStore fileStore;
    private final PhotoRepository photoRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    public List<Photo> savePhotos(List<MultipartFile> imageFiles, String uniqueId) {
        List<Photo> photos = new ArrayList<>();
        for (MultipartFile multipartFile : imageFiles) {
            if (!multipartFile.isEmpty()) {
                Photo uploadFile = fileStore.storeFile(multipartFile, uniqueId);
                Photo savedPhoto = photoRepository.savePhoto(uploadFile);
                photos.add(savedPhoto);
            }
        }
        return photos;
    }

    public byte[] downloadPhotos(String houseId, String email) {
        String uniqueId = email + "-h" + houseId;
        String path = String.format("%s/%s", bucketName, uniqueId);
        List<Photo> photos = photoRepository.getPhotos(path);

        if(photos.size() == 0) return new byte[0];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (Photo photo : photos) {

            try {
                outputStream.write(fileStore.download(path, photo.getStoreFileName()));
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return outputStream.toByteArray();

    }
}
