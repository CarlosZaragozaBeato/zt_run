package com.carloszaragoza.ztrun.common.mapper;

import com.carloszaragoza.ztrun.domain.model.Permission;
import com.carloszaragoza.ztrun.domain.model.Role;
import com.carloszaragoza.ztrun.domain.model.User;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.PermissionEntity;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.RoleEntity;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity e) {
        User u = new User();
        u.setId(e.getId());
        u.setUsername(e.getUsername());
        u.setEmail(e.getEmail());
        u.setPasswordHash(e.getPassword());
        u.setEnabled(e.isEnabled());
        if (e.getRoles() != null) {
            u.setRoles(e.getRoles().stream().map(UserMapper::toDomain).collect(Collectors.toSet()));
        }
        return u;
    }

    public static Role toDomain(RoleEntity e) {
        Role r = new Role();
        r.setId(e.getId());
        r.setName(e.getName());
        r.setDescription(e.getDescription());
        Set<Permission> perms = e.getPermissions() == null ? Set.of() :
                e.getPermissions().stream().map(UserMapper::toDomain).collect(Collectors.toSet());
        r.setPermissions(perms);
        return r;
    }

    public static Permission toDomain(PermissionEntity e) {
        Permission p = new Permission();
        p.setId(e.getId());
        p.setName(e.getName());
        p.setDescription(e.getDescription());
        return p;
    }

    public static UserEntity toEntity(User u) {
        UserEntity e = new UserEntity();
        e.setId(u.getId());
        e.setUsername(u.getUsername());
        e.setEmail(u.getEmail());
        e.setPassword(u.getPasswordHash());
        e.setEnabled(u.isEnabled());
        if (u.getRoles() != null) {
            e.setRoles(u.getRoles().stream().map(UserMapper::toEntity).collect(Collectors.toSet()));
        }
        return e;
    }

    public static RoleEntity toEntity(Role r) {
        RoleEntity e = new RoleEntity();
        e.setId(r.getId());
        e.setName(r.getName());
        e.setDescription(r.getDescription());
        if (r.getPermissions() != null) {
            e.setPermissions(r.getPermissions().stream().map(UserMapper::toEntity).collect(Collectors.toSet()));
        }
        return e;
    }

    public static PermissionEntity toEntity(Permission p) {
        PermissionEntity e = new PermissionEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setDescription(p.getDescription());
        return e;
    }
}
