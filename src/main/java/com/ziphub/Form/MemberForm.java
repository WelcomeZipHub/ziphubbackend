package com.ziphub.Form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MemberForm {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Date createdDate;
}
