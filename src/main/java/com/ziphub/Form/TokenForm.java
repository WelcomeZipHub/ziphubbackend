package com.ziphub.Form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TokenForm {
    private String username;
    private String email;
    private String accessToken;
    private String tokenType;
}
