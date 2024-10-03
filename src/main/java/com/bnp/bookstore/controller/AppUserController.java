package com.bnp.bookstore.controller;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore/user")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser user) {
        AppUser registeredUser = appUserService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

}
