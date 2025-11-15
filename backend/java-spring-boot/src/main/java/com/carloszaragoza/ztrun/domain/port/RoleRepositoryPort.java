package com.carloszaragoza.ztrun.domain.port;

import com.carloszaragoza.ztrun.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {
    Optional<Role> findByName(String name);
}
