package com.carloszaragoza.ztrun.infrastructure.persistence.adapter;

import com.carloszaragoza.ztrun.common.mapper.UserMapper;
import com.carloszaragoza.ztrun.domain.model.Role;
import com.carloszaragoza.ztrun.domain.port.RoleRepositoryPort;
import com.carloszaragoza.ztrun.infrastructure.persistence.repository.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleRepositoryAdapter implements RoleRepositoryPort {

    private final JpaRoleRepository repo;

    public RoleRepositoryAdapter(JpaRoleRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repo.findByName(name).map(UserMapper::toDomain);
    }
}
