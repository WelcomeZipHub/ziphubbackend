package com.ziphub.Controller;

import com.ziphub.Form.LoginForm;
import com.ziphub.Form.MemberForm;
import com.ziphub.Form.MemberRegisterForm;
import com.ziphub.Form.TokenForm;
import com.ziphub.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberForm> register(@Valid @RequestBody MemberRegisterForm form, BindingResult result) {
        MemberForm newMember = memberService.signUp(form.getEmail(), form.getPassword() , form.getPhone());
        return ResponseEntity.ok().body(newMember);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenForm> login(@Valid @RequestBody LoginForm form,  BindingResult result) {
        TokenForm tokenForm = memberService.signIn(form.getEmail(), form.getPassword());
        return ResponseEntity.ok().body(tokenForm);
    }
}
