package com.carloszaragoza.ztrun.domain.service.auth;

import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.repository.auth.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDomainService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDomainService(UserRepository userRepository, PasswordEncoder pe) {
        this.userRepository = userRepository;
        this.passwordEncoder = pe;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User register(User toRegister) {
        // Business validations: unique username/email, password strength, etc.
        toRegister.setPassword(passwordEncoder.encode(toRegister.getPassword()));
        return userRepository.save(toRegister);
    }

    public boolean passwordMatches(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}