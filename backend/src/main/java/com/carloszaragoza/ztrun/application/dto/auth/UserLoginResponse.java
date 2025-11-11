package com.carloszaragoza.ztrun.application.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class UserLoginResponse {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
