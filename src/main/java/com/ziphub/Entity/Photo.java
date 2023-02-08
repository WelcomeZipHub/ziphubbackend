package com.ziphub.Entity;

import com.ziphub.Utils.UploadFile;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Photo {

    @Id
    @GeneratedValue
    private Long id;
    private String uploadFileName;
    private String storeFileName;
    private String storageURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    public Photo(String uploadFileName, String storeFileName, String storageURL) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.storageURL = storageURL;
    }
}
