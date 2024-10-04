package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.AppUserRequestDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppUserControllerTest {

    public static final String BOOKSTORE_USER_REGISTER_URL = "/bookstore/user/register";
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserController appUserController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void registerUser_Success() throws Exception {
        AppUserRequestDTO user = new AppUserRequestDTO("testuser", "password123");
        AppUser appUser = new AppUser("testuser", "password123");

        when(appUserService.registerUser(any(AppUserRequestDTO.class))).thenReturn(appUser);

        mockMvc.perform(post(BOOKSTORE_USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void register_emptyUsername_badRequest() throws Exception {
        AppUserRequestDTO authRequest = new AppUserRequestDTO();
        authRequest.setUsername("");
        authRequest.setPassword("password");

        mockMvc.perform(post(BOOKSTORE_USER_REGISTER_URL)
                        .flashAttr("currentUsername", authRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_emptyPassword_badRequest() throws Exception {
        AppUserRequestDTO authRequest = new AppUserRequestDTO();
        authRequest.setUsername("user");
        authRequest.setPassword("");

        mockMvc.perform(post(BOOKSTORE_USER_REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("currentUsername", authRequest)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_usernameWithSpecialChars_badRequest() throws Exception {
        String username = "user@domain.com";
        String password = "password";

        AppUserRequestDTO authRequest = new AppUserRequestDTO();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        mockMvc.perform(post(BOOKSTORE_USER_REGISTER_URL)
                        .flashAttr("currentUsername", authRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void register_usernameTooLong_badRequest() throws Exception {
        String username = "user".repeat(100);
        String password = "password";

        AppUserRequestDTO authRequest = new AppUserRequestDTO();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        mockMvc.perform(post(BOOKSTORE_USER_REGISTER_URL)
                        .flashAttr("currentUsername", authRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest());
    }

}