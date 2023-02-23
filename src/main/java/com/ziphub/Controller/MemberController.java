package com.ziphub.Controller;

import com.ziphub.Dto.LoginDto;
import com.ziphub.Dto.Member.MemberGetDto;
import com.ziphub.Dto.Member.MemberAddDto;
import com.ziphub.Dto.TokenGetDto;
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

    @PostMapping("/add")
    public ResponseEntity<MemberGetDto> addMember(@Valid @RequestBody MemberAddDto form, BindingResult result) {
        MemberGetDto newMember = memberService.signUp(form.getEmail(), form.getPassword() , form.getPhone());
        return ResponseEntity.ok().body(newMember);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenGetDto> login(@Valid @RequestBody LoginDto form, BindingResult result) {
        TokenGetDto tokenForm = memberService.signIn(form.getEmail(), form.getPassword());
        return ResponseEntity.ok().body(tokenForm);
    }
}
