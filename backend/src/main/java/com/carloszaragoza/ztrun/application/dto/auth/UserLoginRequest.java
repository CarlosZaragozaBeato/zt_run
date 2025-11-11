package com.carloszaragoza.ztrun.application.dto.auth;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
