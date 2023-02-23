package com.ziphub.Entity;
import com.ziphub.Dto.Member.MemberGetDto;
import com.ziphub.Dto.TokenGetDto;
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
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;

    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<House> houses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    private LocalDateTime createdDate;

    public static MemberGetDto createMemberForm(Member validatedMember) {
        MemberGetDto newMember = new MemberGetDto(
                validatedMember.getId(),
                validatedMember.getEmail(),
                validatedMember.getPhone(),
                validatedMember.getCreatedDate()
        );
        return newMember;
    }

    public static TokenGetDto createTokenForm(String token) {
        TokenGetDto tokenForm = TokenGetDto.builder()
                .accessTime(LocalDateTime.now())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
        return tokenForm;
    }
}
