package com.carloszaragoza.ztrun.infrastructure.web.controller;

import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.domain.port.AuthServicePort;
import com.carloszaragoza.ztrun.infrastructure.web.dto.LoginRequest;
import com.carloszaragoza.ztrun.infrastructure.web.dto.LoginResponse;
import com.carloszaragoza.ztrun.infrastructure.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServicePort authService;

    public AuthController(AuthServicePort authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.getUsernameOrEmail(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Registrar el usuario
        User user = authService.register(request.getUsername(), request.getEmail(), request.getPassword());

        // Generar token de sesión inmediatamente después del registro
        String token = authService.login(user.getEmail(), request.getPassword());

        // Devolver el token como en el login
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
