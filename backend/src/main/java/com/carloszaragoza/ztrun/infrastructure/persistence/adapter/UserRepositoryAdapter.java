package com.carloszaragoza.ztrun.infrastructure.persistence.adapter;

import com.carloszaragoza.ztrun.common.mapper.UserMapper;
import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.domain.port.UserRepositoryPort;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.UserEntity;
import com.carloszaragoza.ztrun.infrastructure.persistence.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository repo;

    public UserRepositoryAdapter(JpaUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity saved = repo.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(saved);
    }
}
