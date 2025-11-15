package com.carloszaragoza.ztrun.domain.port;

import com.carloszaragoza.ztrun.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
}