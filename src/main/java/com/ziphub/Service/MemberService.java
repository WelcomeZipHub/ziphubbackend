package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Exception.ErrorCode;
import com.ziphub.Exception.MemberException;
import com.ziphub.Form.MemberForm;
import com.ziphub.Form.TokenForm;
import com.ziphub.Repository.MemberRepository;
import com.ziphub.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Value("${jwt.token.secretKey}")
    private String secretkey;
    @Value(("${jwt.token.expiredMs}"))
    private Long expiredTimeMs;

    @Transactional
    public MemberForm signUp(String username, String password, String email, String phone) {
        validateMember(username);

        Member member = Member.builder()
                .username(username)
                .password(bcryptEncoder.encode(password))
                .email(email)
                .phone(phone)
                .createdDate(new Date(System.currentTimeMillis()))
                .build();

        Member validatedMember = memberRepository.save(member);

        MemberForm newMember = new MemberForm(
                validatedMember.getId(),
                validatedMember.getUsername(),
                validatedMember.getEmail(),
                validatedMember.getPhone(),
                validatedMember.getCreatedDate()
        );

        return newMember;
    }

    public TokenForm signIn(String username, String password) {
        Member selectedMember = memberRepository.findOneByUsername(username).orElseThrow(() -> new MemberException(ErrorCode.USERNAME_NOT_FOUND, ""));

        if (!bcryptEncoder.matches(password, selectedMember.getPassword())) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD, "");
        }

        String token = JwtUtil.createJwt(selectedMember.getUsername(), secretkey, expiredTimeMs);
        TokenForm tokenForm = TokenForm.builder()
                .username(selectedMember.getUsername())
                .email(selectedMember.getEmail())
                .accessToken(token)
                .tokenType("Bearer")
                .build();

        return tokenForm;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validateMember(String username) {
        memberRepository.findOneByUsername(username)
                .ifPresent(user -> { throw new MemberException(ErrorCode.USERNAME_DUPLICATED, ""); });
    }
}
