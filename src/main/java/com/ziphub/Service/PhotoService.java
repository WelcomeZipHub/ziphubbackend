package com.ziphub.Service;

import com.ziphub.Entity.Photo;
import com.ziphub.Repository.PhotoRepository;
import com.ziphub.Utils.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final FileStore fileStore;
    private final PhotoRepository photoRepository;

    public List<Photo> savePhotos(List<MultipartFile> imageFiles) throws IOException {
        List<Photo> photos = new ArrayList<>();

        for (MultipartFile multipartFile : imageFiles) {
            if (!multipartFile.isEmpty()) {
                Photo uploadFile = fileStore.storeFile(multipartFile);
                Photo savedPhoto = photoRepository.savePhoto(uploadFile);
                photos.add(savedPhoto);
            }
        }
        return photos;
    }
}
