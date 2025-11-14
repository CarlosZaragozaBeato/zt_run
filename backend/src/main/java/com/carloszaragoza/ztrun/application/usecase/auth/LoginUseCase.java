package com.carloszaragoza.ztrun.application.usecase.auth;


import com.carloszaragoza.ztrun.application.dto.auth.LoginRequest;
import com.carloszaragoza.ztrun.application.dto.auth.LoginResponse;
import com.carloszaragoza.ztrun.domain.exception.InvalidCredentialsException;
import com.carloszaragoza.ztrun.domain.model.auth.ActivityLog;
import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.repository.auth.ActivityLogRepository;
import com.carloszaragoza.ztrun.domain.repository.auth.UserRepository;
import com.carloszaragoza.ztrun.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginUseCase {

    private final UserRepository userRepository;
    private final ActivityLogRepository activityLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginUseCase(UserRepository userRepository,
                        ActivityLogRepository activityLogRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.activityLogRepository = activityLogRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse execute(LoginRequest request) {
        // 1. Buscar usuario por username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        // 2. Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // Registrar intento fallido
            ActivityLog failedLog = ActivityLog.create(
                    user.getId(),
                    "LOGIN_FAILED",
                    request.getIpAddress()
            );
            activityLogRepository.save(failedLog);

            throw new InvalidCredentialsException("Invalid username or password");
        }

        // 3. Verificar si está habilitado
        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("User account is disabled");
        }

        // 4. Generar token JWT
        String token = jwtTokenProvider.generateToken(user);

        // 5. Registrar login exitoso
        ActivityLog successLog = ActivityLog.create(
                user.getId(),
                "LOGIN_SUCCESS",
                request.getIpAddress()
        );
        activityLogRepository.save(successLog);

        // 6. Retornar respuesta
        return new LoginResponse(token, user.getUsername(), user.getRoles());
    }
}