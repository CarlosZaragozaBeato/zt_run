package com.carloszaragoza.ztrun.infrastructure.config;

import com.carloszaragoza.ztrun.application.usecase.auth.LoginUseCase;
import com.carloszaragoza.ztrun.application.usecase.auth.RegisterUseCase;
import com.carloszaragoza.ztrun.domain.repository.auth.ActivityLogRepository;
import com.carloszaragoza.ztrun.domain.repository.auth.UserRepository;
import com.carloszaragoza.ztrun.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            ActivityLogRepository activityLogRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        return new LoginUseCase(
                userRepository,
                activityLogRepository,
                passwordEncoder,
                jwtTokenProvider
        );
    }

    @Bean
    public RegisterUseCase registerUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        return new RegisterUseCase(
                userRepository,
                passwordEncoder,
                jwtTokenProvider
        );
    }
}
