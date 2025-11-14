package com.carloszaragoza.ztrun.application.usecase.auth;


import com.carloszaragoza.ztrun.application.dto.auth.LoginResponse;
import com.carloszaragoza.ztrun.application.dto.auth.RegisterRequest;
import com.carloszaragoza.ztrun.domain.exception.DuplicateUserException;
import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.repository.auth.UserRepository;
import com.carloszaragoza.ztrun.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public RegisterUseCase(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse execute(RegisterRequest request) {
        // 1. Verificar si ya existe el username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already exists");
        }

        // 2. Verificar si ya existe el email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already exists");
        }

        // 3. Crear usuario
        User user = User.create(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        // 4. Guardar usuario
        User savedUser = userRepository.save(user);

        // 5. Generar token
        String token = jwtTokenProvider.generateToken(savedUser);

        // 6. Retornar respuesta
        return new LoginResponse(token, savedUser.getUsername(), savedUser.getRoles());
    }
}