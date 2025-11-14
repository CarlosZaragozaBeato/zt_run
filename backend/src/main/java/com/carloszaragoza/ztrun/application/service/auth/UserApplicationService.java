package com.carloszaragoza.ztrun.application.service.auth;


import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.service.auth.UserDomainService;
import com.carloszaragoza.ztrun.infrastructure.configuration.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserApplicationService {

    private final UserDomainService domainService;
    private final JwtTokenProvider jwt;

    public UserApplicationService(UserDomainService domainService, JwtTokenProvider jwt) {
        this.domainService = domainService;
        this.jwt = jwt;
    }

    public UserLoginResponse login(UserLoginRequest req) {
        User user = domainService.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!domainService.passwordMatches(user, req.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        String token = jwt.generateToken(user.getUsername(), roles);
        long expiresIn = jwt.getExpiryMillis();

        return new UserLoginResponse(token, "Bearer",expiresIn);
    }
}