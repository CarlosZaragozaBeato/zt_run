package com.carloszaragoza.ztrun.shared.mapper;

import com.carloszaragoza.ztrun.domain.model.auth.Permission;
import com.carloszaragoza.ztrun.domain.model.auth.Role;
import com.carloszaragoza.ztrun.domain.model.auth.User;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.auth.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toDomain(UserEntity e) {
        if (e == null) return null;
        return User.builder()
                .id(e.getId())
                .username(e.getUsername())
                .email(e.getEmail())
                .password(e.getPassword())
                .enabled(e.isEnabled())
                .createdAt(e.getCreatedAt())
                .roles(
                        e.getRoles().stream()
                                .map(roleEntity -> {
                                    Role role = new Role();
                                    role.setId(roleEntity.getId());
                                    role.setName(roleEntity.getName());
                                    // También mapeamos los permisos
                                    role.setPermissions(
                                            roleEntity.getPermissions().stream()
                                                    .map(p -> {
                                                        Permission perm = new Permission();
                                                        perm.setId(p.getId());
                                                        perm.setName(p.getName());
                                                        return perm;
                                                    })
                                                    .collect(Collectors.toSet())
                                    );
                                    return role;
                                })
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public static UserEntity toEntity(User d) {
        if (d == null) return null;
        UserEntity e = new UserEntity();
        e.setId(d.getId());
        e.setUsername(d.getUsername());
        e.setEmail(d.getEmail());
        e.setPassword(d.getPassword());
        e.setEnabled(d.isEnabled());
        e.setCreatedAt(d.getCreatedAt());
        // roles mapping left to repository/service (fetch roles from DB)
        return e;
    }
}
