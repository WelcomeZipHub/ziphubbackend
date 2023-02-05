package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String username, String password) {
        Member member = memberRepository.findOneByUsername(username);

        if(member == null) {
            return null;
        }

        if (member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }

}
