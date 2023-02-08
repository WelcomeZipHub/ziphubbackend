package com.ziphub.Entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(generator = "uuid")

    private Long id;

    private String email;
    private String phone;
    private String username;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    private LocalDateTime createdDate;

}
