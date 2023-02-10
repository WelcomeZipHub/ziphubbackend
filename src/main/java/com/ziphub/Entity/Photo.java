package com.ziphub.Entity;
import lombok.*;
import javax.persistence.*;


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
    private String storage_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;
}
