package com.ziphub.Service;

import com.ziphub.Entity.Member;
import com.ziphub.Form.RegisterForm;
import com.ziphub.Repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void signUp() throws Exception {

    }

    @Test(expected = IllegalStateException.class)
    public void exceptionDuplicate() throws Exception {
//        Member m1 = new Member();
//        Member m2 = new Member();
//        m1.setEmail("aaa@aaa.com");
//        m2.setEmail("aaa@aaa.com");
//
//        memberService.signUp(m1);
//        memberService.signUp(m2);

        fail("The test must throw an exception");
    }
}