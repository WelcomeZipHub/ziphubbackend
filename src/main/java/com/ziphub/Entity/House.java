package com.ziphub.Entity;

import com.ziphub.Entity.Embedded.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class House {

    @Id
    @Column(name = "house_id")
    @GeneratedValue
    private Long id;

    @Embedded
    Address address;

    private int price;
    private String description;

    @OneToMany(mappedBy = "house")
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "house")
    private List<Favorite> favorites = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createdDate;
}
