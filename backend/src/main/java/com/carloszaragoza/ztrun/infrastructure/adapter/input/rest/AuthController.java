package com.carloszaragoza.ztrun.infrastructure.adapter.input.rest;

import com.carloszaragoza.ztrun.application.dto.auth.LoginRequest;
import com.carloszaragoza.ztrun.application.dto.auth.LoginResponse;
import com.carloszaragoza.ztrun.application.dto.auth.RegisterRequest;
import com.carloszaragoza.ztrun.application.usecase.auth.LoginUseCase;
import com.carloszaragoza.ztrun.application.usecase.auth.RegisterUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO,
            HttpServletRequest servletRequest) {

        String ipAddress = getClientIP(servletRequest);

        LoginRequest request = new LoginRequest(
                requestDTO.username(),
                requestDTO.password(),
                ipAddress
        );

        LoginResponse response = loginUseCase.execute(request);

        AuthResponseDTO responseDTO = new AuthResponseDTO(
                response.getToken(),
                response.getUsername(),
                response.getRoles()
        );

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {

        RegisterRequest request = new RegisterRequest(
                requestDTO.username(),
                requestDTO.email(),
                requestDTO.password()
        );

        LoginResponse response = registerUseCase.execute(request);

        AuthResponseDTO responseDTO = new AuthResponseDTO(
                response.getToken(),
                response.getUsername(),
                response.getRoles()
        );

        return ResponseEntity.ok(responseDTO);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}