package com.carloszaragoza.ztrun.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponse(String token) { this.accessToken = token; }

}
