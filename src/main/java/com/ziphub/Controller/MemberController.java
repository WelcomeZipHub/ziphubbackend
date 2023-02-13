package com.ziphub.Controller;

import com.ziphub.Dto.LoginDto;
import com.ziphub.Dto.MemberDto;
import com.ziphub.Dto.MemberRegisterDto;
import com.ziphub.Dto.TokenDto;
import com.ziphub.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberDto> register(@Valid @RequestBody MemberRegisterDto form, BindingResult result) {
        MemberDto newMember = memberService.signUp(form.getEmail(), form.getPassword() , form.getPhone());
        return ResponseEntity.ok().body(newMember);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto form, BindingResult result) {
        TokenDto tokenForm = memberService.signIn(form.getEmail(), form.getPassword());
        return ResponseEntity.ok().body(tokenForm);
    }
}
