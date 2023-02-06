package com.ziphub.Controller;

import com.ziphub.Entity.Member;
import com.ziphub.Form.LoginForm;
import com.ziphub.Form.RegisterForm;
import com.ziphub.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@Valid @RequestBody RegisterForm form, BindingResult result) {
        Long memberId = memberService.signUp(form.getUsername(), form.getPassword(), form.getEmail(), form.getPhone());
        return ResponseEntity.ok().body(memberId);
    }
    @PostMapping("/login")
    public ResponseEntity<Member> login(@Valid @RequestBody LoginForm form, BindingResult result) {
        Member member = memberService.signIn(form.getUsername(), form.getPassword());
        return ResponseEntity.ok().body(member);
    }
}
