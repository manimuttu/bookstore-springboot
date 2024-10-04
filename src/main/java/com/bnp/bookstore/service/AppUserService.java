package com.bnp.bookstore.service;

import com.bnp.bookstore.dto.AppUserRequestDTO;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<AppUser> findByUsername(String username){
        return appUserRepository.findByUsername(username);
    }

    public AppUser registerUser(AppUserRequestDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser appuser = new AppUser(user.getUsername(), user.getPassword());
        return appUserRepository.save(appuser);
    }

    public boolean authenticate(String username, String password) {
        Optional<AppUser> optionalUser = appUserRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true; // Authentication successful
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
