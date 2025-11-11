package com.carloszaragoza.ztrun.infrastructure.repository.auth;

import com.carloszaragoza.ztrun.infrastructure.persistence.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
