package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppUserController.class)
public class AppUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerUser_Success() throws Exception {
        AppUser user = new AppUser("testuser", "password123");

        when(appUserService.registerUser(any(AppUser.class))).thenReturn(user);

        mockMvc.perform(post("/bookstore/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }
}