package com.ziphub.Entity;
import com.ziphub.Dto.MemberDto;
import com.ziphub.Dto.TokenDto;
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

    public static MemberDto createMemberForm(Member validatedMember) {
        MemberDto newMember = new MemberDto(
                validatedMember.getId(),
                validatedMember.getEmail(),
                validatedMember.getPhone(),
                validatedMember.getCreatedDate()
        );
        return newMember;
    }

    public static TokenDto createTokenForm(String token) {
        TokenDto tokenForm = TokenDto.builder()
                .accessTime(LocalDateTime.now())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
        return tokenForm;
    }
}
