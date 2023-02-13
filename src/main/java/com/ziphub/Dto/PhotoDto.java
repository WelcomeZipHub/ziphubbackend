package com.ziphub.Dto;

import com.ziphub.Entity.Photo;
import lombok.Data;

@Data
public class PhotoDto {
    private Long photoId;
    private String uploadFileName;
    private String storeFileName;
    private String storage_url;

    public PhotoDto(Photo photo) {
        photoId = photo.getId();
        uploadFileName = photo.getUploadFileName();
        storeFileName = photo.getStoreFileName();
        storage_url = photo.getStorage_url();
    }
}
