package com.carloszaragoza.ztrun.application.service;

import com.carloszaragoza.ztrun.domain.exception.login.InvalidCredentialsException;
import com.carloszaragoza.ztrun.domain.model.Role;
import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.domain.port.AuthServicePort;
import com.carloszaragoza.ztrun.domain.port.RoleRepositoryPort;
import com.carloszaragoza.ztrun.domain.port.UserRepositoryPort;
import com.carloszaragoza.ztrun.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServicePort {

    private final UserRepositoryPort userRepo;
    private final RoleRepositoryPort roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwt;

    public AuthServiceImpl(UserRepositoryPort userRepo,
                           RoleRepositoryPort roleRepo,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwt) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @Override
    public String login(String usernameOrEmail, String passwordPlain) {
        User user = userRepo.findByUsername(usernameOrEmail)
                .or(() -> userRepo.findByEmail(usernameOrEmail))
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isEnabled() || !passwordEncoder.matches(passwordPlain, user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return jwt.createToken(user);
    }


    @Override
    public User register(String username, String email, String passwordPlain) {
        if (userRepo.findByUsername(username).isPresent() || userRepo.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Usuario o email ya existen");
        }
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(passwordPlain));
        u.setEnabled(true);

        // Asignar rol por defecto "USER"
        Role defaultRole = roleRepo.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Rol USER no existe"));
        u.setRoles(java.util.Set.of(defaultRole));

        return userRepo.save(u);
    }
}
