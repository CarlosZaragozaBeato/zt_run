package com.carloszaragoza.ztrun.infrastructure.persistence.adapter;


import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.domain.repository.auth.UserRepository;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.auth.UserEntity;
import com.carloszaragoza.ztrun.infrastructure.repository.auth.JpaUserRepository;
import com.carloszaragoza.ztrun.shared.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringDataUserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpa;

    public SpringDataUserRepositoryAdapter(JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        // Keep roles handling outside or implement role fetch and set here
        UserEntity saved = jpa.save(entity);
        return UserMapper.toDomain(saved);
    }
}