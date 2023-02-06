package com.ziphub.Form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotEmpty(message = "Please provide email")
    @Size(min = 4, max = 30)
    private String email;

    @NotEmpty(message = "Please provide phone")
    @Size(min = 13, max = 13)
    private String phone;

    @NotEmpty(message = "Please provide username")
    @Size(min = 4, max = 20)
    private String username;

    @NotEmpty(message = "Please provide password")
    @Size(min = 4, max = 20)
    private String password;

    @NotEmpty(message = "Please provide firstname")
    @Size(min = 3, max = 20)
    private String firstname;

    @NotEmpty(message = "Please provide lastname")
    @Size(min = 3, max = 20)
    private String lastname;
}
