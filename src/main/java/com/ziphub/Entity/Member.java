package com.ziphub.Entity;
import com.ziphub.Form.MemberForm;
import com.ziphub.Form.TokenForm;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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

    public static MemberForm createMemberForm(Member validatedMember) {
        MemberForm newMember = new MemberForm(
                validatedMember.getId(),
                validatedMember.getEmail(),
                validatedMember.getPhone(),
                validatedMember.getCreatedDate()
        );
        return newMember;
    }

    public static TokenForm createTokenForm(String token) {
        TokenForm tokenForm = TokenForm.builder()
                .accessTime(LocalDateTime.now())
                .accessToken(token)
                .tokenType("Bearer")
                .build();
        return tokenForm;
    }
}
