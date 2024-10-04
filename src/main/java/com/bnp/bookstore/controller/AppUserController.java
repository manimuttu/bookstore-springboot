package com.bnp.bookstore.controller;

import com.bnp.bookstore.dto.AppUserRequestDTO;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.service.AppUserService;
import com.bnp.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookstore/user")
public class AppUserController {

    private AuthenticationManager authenticationManager;

    private AppUserService appUserService;

    @Autowired
    public AppUserController(AuthenticationManager authenticationManager, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody AppUserRequestDTO userDTO) {
        if (appUserService.findByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        AppUser registeredUser = appUserService.registerUser(userDTO);
        return ResponseEntity.ok("User registered");
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@ModelAttribute("currentUsername") Object loggedInUser) {
        String loggedInUsername = ((AppUser)loggedInUser).getUsername();
        String token = JwtUtil.generateToken(loggedInUsername);
        return ResponseEntity.ok(token);
    }
}
