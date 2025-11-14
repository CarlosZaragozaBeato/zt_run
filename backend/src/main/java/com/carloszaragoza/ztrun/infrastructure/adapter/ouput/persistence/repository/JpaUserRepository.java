package com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles r JOIN FETCH r.permissions WHERE u.username = :username")
    Optional<UserEntity> findByUsernameWithRolesAndPermissions(@Param("username") String username);
}