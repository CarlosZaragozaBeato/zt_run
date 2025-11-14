package com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
    boolean existsByName(String name);
}