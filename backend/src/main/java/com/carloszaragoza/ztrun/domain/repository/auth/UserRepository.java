package com.carloszaragoza.ztrun.domain.repository.auth;

import com.carloszaragoza.ztrun.domain.model.auth.User;

import java.util.Optional;


public interface UserRepository {
    Optional<User> findByUsername(String username);
}
