package com.carloszaragoza.ztrun.presentation.controller.auth;

import com.carloszaragoza.ztrun.application.service.auth.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserApplicationService service;

    public AuthController(UserApplicationService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest req) {
        UserLoginResponse resp = service.login(req);
        return ResponseEntity.ok(resp);
    }
}
