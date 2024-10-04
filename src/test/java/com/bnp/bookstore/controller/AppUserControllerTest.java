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
        AppUser user = new AppUser("testuser", "password123");

        when(appUserService.registerUser(any(AppUserRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/bookstore/user/register")
//                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

//    @Test
//    public void registerUser_Success() throws Exception {
//        AppUser user = new AppUser("testuser", "password123");
//
//        when(appUserService.registerUser(any(AppUserRequestDTO.class))).thenReturn(user);
//
//        mockMvc.perform(post("/bookstore/user/register")
////                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(user)))
//                .andExpect(status().isOk());
//    }
}