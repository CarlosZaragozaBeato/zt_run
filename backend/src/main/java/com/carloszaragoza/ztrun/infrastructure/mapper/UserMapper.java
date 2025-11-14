package com.carloszaragoza.ztrun.infrastructure.mapper;

import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;

    public UserMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();
        user.setId(entity.getId());
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setEnabled(entity.getEnabled());
        user.setCreatedAt(entity.getCreatedAt());

        if (entity.getRoles() != null) {
            entity.getRoles().forEach(roleEntity ->
                    user.assignRole(roleMapper.toDomain(roleEntity))
            );
        }

        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setEnabled(user.isEnabled());
        entity.setCreatedAt(user.getCreatedAt());

        if (user.getRoles() != null) {
            user.getRoles().forEach(role ->
                    entity.getRoles().add(roleMapper.toEntity(role))
            );
        }

        return entity;
    }
}