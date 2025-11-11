package com.carloszaragoza.ztrun.infrastructure.repository.auth;

import com.carloszaragoza.ztrun.infrastructure.persistence.entity.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}