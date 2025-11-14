package com.carloszaragoza.ztrun.application.dto.auth;

import com.carloszaragoza.ztrun.domain.model.auth.Role;

import java.util.Set;
import java.util.stream.Collectors;

public class LoginResponse {
    private String token;
    private String username;
    private Set<String> roles;

    public LoginResponse(String token, String username, Set<Role> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    // Getters
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public Set<String> getRoles() { return roles; }
}