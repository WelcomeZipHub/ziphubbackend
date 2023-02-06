package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Form.LoginForm;
import com.ziphub.Form.RegisterForm;
import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long signUp(String username, String password, String email, String phone) {
        validateMember(username);
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .phone(phone)
                .createdDate(new Date(System.currentTimeMillis()))
                .build();

        return memberRepository.save(member);
    }

    public Member signIn(String username, String password) {
        return memberRepository.findOneByUsername(username)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validateMember(String username) {
        memberRepository.findOneByUsername(username)
        .ifPresent(user -> { throw new IllegalStateException("Your username is already taken"); });
    }
}
