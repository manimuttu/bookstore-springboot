package com.bnp.bookstore.service;

import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser registerUser(AppUser user) {
        return appUserRepository.save(user);
    }
}
