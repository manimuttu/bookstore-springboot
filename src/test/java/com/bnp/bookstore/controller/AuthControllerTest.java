package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    public static final String LOGIN_URL = "/bookstore/user/login";
    @InjectMocks
    private AppUserController appUserController;

    @Mock
    private AppUserService appUserService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();
    }

    @Test
    public void login_validCredentials_tokenReturned() throws Exception {
        String username = "user";
        String password = "password";

        AppUser authRequest = new AppUser();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        mockMvc.perform(get(LOGIN_URL)
                        .flashAttr("currentUsername", authRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }
}
