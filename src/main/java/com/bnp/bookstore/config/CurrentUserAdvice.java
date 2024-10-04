package com.bnp.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Slf4j
public class CurrentUserAdvice {

    @ModelAttribute("currentUsername")
    public Object getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication.getPrincipal() : {}", authentication.getPrincipal());
        return (authentication != null) ? authentication.getPrincipal() : null;
    }
}
