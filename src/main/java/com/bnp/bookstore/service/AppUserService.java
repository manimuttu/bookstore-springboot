package com.bnp.bookstore.service;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public Optional<AppUser> findByUsername(String username){
        return appUserRepository.findByUsername(username);
    }

    public AppUser registerUser(AppUser user) {
        return appUserRepository.save(user);
    }
}
