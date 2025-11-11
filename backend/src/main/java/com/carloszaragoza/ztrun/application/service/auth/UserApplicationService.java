package com.carloszaragoza.ztrun.application.service.auth;


import com.carloszaragoza.ztrun.application.dto.auth.UserLoginResponse;
import com.carloszaragoza.ztrun.application.dto.auth.UserLoginRequest;
import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.service.auth.UserDomainService;
import com.carloszaragoza.ztrun.infrastructure.configuration.JwtTokenProvider;
import org.springframework.stereotype.Service;

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

        String token = jwt.createToken(user.getUsername(), user.getRoles());
        return new UserLoginResponse(token, "Bearer", jwt.getExpiryMillis());
    }
}