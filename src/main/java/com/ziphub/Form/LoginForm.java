package com.ziphub.Form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @NotEmpty(message = "Please provide username")
    @Size(min = 4, max = 20)
    private String username;

    @NotEmpty(message = "Please provide password")
    @Size(min = 4, max = 20)
    private String password;
}
