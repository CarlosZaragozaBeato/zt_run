package com.carloszaragoza.ztrun.domain.repository.auth;

import com.carloszaragoza.ztrun.domain.model.auth.Role;

import java.util.Optional;


public interface RoleRepository {
    Optional<Role> findByName(String name);
}