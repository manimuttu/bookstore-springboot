package com.bnp.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AppUserRequestDTO {

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$",
            message = "Username must be between 3 and 20 characters and can only contain letters, digits, underscores, and hyphens.")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public AppUserRequestDTO() {}

    public AppUserRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
