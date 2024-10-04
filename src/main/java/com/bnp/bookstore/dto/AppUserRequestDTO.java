package com.bnp.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppUserRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must not exceed 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public AppUserRequestDTO() {}

    public AppUserRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
