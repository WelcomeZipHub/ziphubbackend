package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Exception.ErrorCode;
import com.ziphub.Exception.MemberException;
import com.ziphub.Dto.Member.MemberGetDto;
import com.ziphub.Dto.TokenGetDto;
import com.ziphub.Repository.MemberRepository;
import com.ziphub.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bcryptEncoder;

    @Value("${jwt.token.secretKey}")
    private String secretKey;
    @Value(("${jwt.token.expiredMs}"))
    private Long expiredTimeMs;

    @Transactional
    public MemberGetDto signUp(String email, String password, String phone) {
        validateMember(email);

        Member member = Member.builder()
                .email(email)
                .password(bcryptEncoder.encode(password))
                .phone(phone)
                .createdDate(LocalDateTime.now())
                .build();

        Member validatedMember = memberRepository.save(member);
        return Member.createMemberForm(validatedMember);
    }

    public TokenGetDto signIn(String email, String password) {
        Member selectedMember = memberRepository.findOneByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.USERNAME_NOT_FOUND, ""));

        if (!bcryptEncoder.matches(password, selectedMember.getPassword())) {
            throw new MemberException(ErrorCode.INVALID_PASSWORD, "");
        }

        String token = JwtUtil.createJwt(selectedMember.getEmail(), secretKey, expiredTimeMs);

        return Member.createTokenForm(token);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validateMember(String email) {
        memberRepository.findOneByEmail(email)
                .ifPresent(user -> { throw new MemberException(ErrorCode.USERNAME_DUPLICATED, ""); });
    }
}
