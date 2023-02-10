package com.ziphub.Entity;
import com.ziphub.Entity.Embedded.Address;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class House {

    @Id
    @Column(name = "house_id")
    @GeneratedValue
    private Long id;

    @Embedded
    Address address;

    private int price;
    private String description;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<Photo> photos = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createdDate;

    public void setMember(Member member) {
        this.member = member;
        member.getHouses().add(this);
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setHouse(this);
    }
}
