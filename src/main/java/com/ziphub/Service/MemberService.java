package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Form.LoginForm;
import com.ziphub.Form.RegisterForm;
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

    @Transactional
    public Long signUp(RegisterForm form) {
        validateMember(form);
        Long memberId = memberRepository.save(form);
        return memberId;
    }

    public Member signIn(LoginForm form) {
        return memberRepository.findOneByUsername(form.getUsername())
                .filter(m -> m.getPassword().equals(form.getPassword()))
                .orElse(null);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    private void validateMember(RegisterForm form) {
        Optional<Member> user = memberRepository.findOneByEmail(form.getEmail());
        if(user.isPresent()) throw new IllegalStateException("The email is already in used");
    }
}
