package com.ziphub.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String phone;
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    private LocalDateTime createdDate;
}
