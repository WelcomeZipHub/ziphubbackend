package com.ziphub.Form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class LoginForm {
    @NotEmpty(message = "Please provide email")
    @Size(min = 4, max = 20)
    private String email;

    @NotEmpty(message = "Please provide password")
    @Size(min = 4, max = 20)
    private String password;
}
