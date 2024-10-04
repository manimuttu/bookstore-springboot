package com.bnp.bookstore.security;

import com.bnp.bookstore.exception.CustomBadRequestException;
import com.bnp.bookstore.model.AppUser;
import com.bnp.bookstore.service.AppUserService;
import com.bnp.bookstore.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null) {
            if (authorizationHeader.startsWith("Bearer ")) {
                // Handle JWT
                jwt = authorizationHeader.substring(7);
                username = JwtUtil.extractUsername(jwt);
                handleJwtLogin(username, jwt, request);
            } else if (request.getRequestURI().equals("/bookstore/user/login") && authorizationHeader.startsWith("Basic ")) {
                // Handle Basic Authentication
                String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
                String credentials = new String(Base64.getDecoder().decode(base64Credentials));
                // credentials = "username:password"
                final String[] values = credentials.split(":", 2);
                username = values[0];
                String password = values[1];

                // Authenticate the user
                Optional<AppUser> userDetails = userService.findByUsername(username);
                if (userDetails.isEmpty() || !userService.authenticate(username, password)) {
                    throw new CustomBadRequestException("Invalid credentials");
                }

                setUserInAuthContext(request, userDetails);
                // Optionally, if using JWT, you could generate a token here.
            }
        }

        chain.doFilter(request, response);
    }

    private void handleJwtLogin(String username, String jwt, HttpServletRequest request) {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<AppUser> userDetails = userService.findByUsername(username);

            userDetails.orElseThrow(() -> new CustomBadRequestException("User not found to login : " + username));

            if (JwtUtil.validateToken(jwt, userDetails.get().getUsername())) {
                setUserInAuthContext(request, userDetails);
            }
        }
    }

    private void setUserInAuthContext(HttpServletRequest request, Optional<AppUser> userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails.get(), null, null);
        usernamePasswordAuthenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
