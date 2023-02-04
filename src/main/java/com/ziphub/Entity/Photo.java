package com.ziphub.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Photo {
    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    @Lob
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    private LocalDateTime createdDate;
}
