package com.ziphub.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziphub.Exception.ErrorCode;
import com.ziphub.Exception.MemberException;
import com.ziphub.Form.LoginForm;
import com.ziphub.Form.MemberRegisterForm;
import com.ziphub.Form.TokenForm;
import com.ziphub.Service.MemberService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("Successfully registered")
    @WithMockUser
    public void sign_up_success() throws Exception {
        String username = "ghghgngngkgk";
        String password = "123456";
        String email = "joon@gmail.com";
        String phone = "909-703-1010";

        MemberRegisterForm memberRegisterForm = new MemberRegisterForm(username, password, email, phone);

        mockMvc.perform(post("/api/member/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberRegisterForm)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Failed to registered due to username already in used")
    @WithMockUser
    public void sign_up_fail() throws Exception {
        String username = "ghghgngngkgk";
        String password = "123456";
        String email = "joon@gmail.com";
        String phone = "909-703-1010";

        MemberRegisterForm memberRegisterForm = new MemberRegisterForm(username, password, email, phone);

        when(memberService.signUp(anyString(), anyString(), anyString(), anyString())).thenThrow(new MemberException(ErrorCode.USERNAME_DUPLICATED, "Member already existed"));

        mockMvc.perform(post("/api/member/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRegisterForm)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @DisplayName("Successful login")
    @WithMockUser
    public void login_success() throws Exception {
        String username = "ghghgngngkgk";
        String password = "123456";

        LoginForm loginForm = new LoginForm(username, password);
        TokenForm tokenForm = new TokenForm(LocalDateTime.now(),  "token", "Bearer");

        when(memberService.signIn(anyString(), anyString())).thenReturn(tokenForm);

        mockMvc.perform(post("/api/member/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Failed to login - username not exist")
    @WithMockUser
    public void login_fail_username() throws Exception {
        String username = "whoareyou";
        String password = "123456";

        LoginForm loginForm = new LoginForm(username, password);

        when(memberService.signIn(anyString(), anyString())).thenThrow(new MemberException(ErrorCode.USERNAME_NOT_FOUND, "username not found"));

        mockMvc.perform(post("/api/member/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("Failed to login - password doesn't match")
    @WithMockUser
    public void login_fail_password() throws Exception {
        String username = "ghghgngngkgk";
        String password = "654321";

        LoginForm loginForm = new LoginForm(username, password);

        when(memberService.signIn(anyString(), anyString())).thenThrow(new MemberException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("/api/member/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginForm)))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }
}
