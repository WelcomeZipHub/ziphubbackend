package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginService loginService;

    @Transactional
    public Long signUp(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public Long signIn(Member member) {
        return 0L;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validateMember(Member member) {
        Optional<Member> user = memberRepository.findOneByEmail(member.getEmail());
        user.orElseThrow(() -> new IllegalStateException("The email is already in used"));
    }
}
