package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

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
        List<Member> findEmail = memberRepository.findOneByEmail(member.getEmail());
        if(!findEmail.isEmpty()) throw new IllegalStateException("The email is already in used");
    }
}
