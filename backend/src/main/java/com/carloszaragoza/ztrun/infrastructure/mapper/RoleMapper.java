package com.carloszaragoza.ztrun.infrastructure.mapper;

import com.carloszaragoza.ztrun.domain.model.auth.Role;
import com.carloszaragoza.ztrun.domain.repository.auth.RoleRepository;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public RoleRepository toDomain(RoleEntity entity) {
        if (entity == null) return null;

        Role role = new Role();
        role.setId(entity.getId());
        role.setName(entity.getName());
        role.setDescription(entity.getDescription());

        if (entity.getPermissions() != null) {
            entity.getPermissions().forEach(permissionEntity ->
                    role.addPermission(permissionMapper.toDomain(permissionEntity))
            );
        }

        return role;
    }

    public RoleEntity toEntity(Role role) {
        if (role == null) return null;

        RoleEntity entity = new RoleEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        entity.setDescription(role.getDescription());

        if (role.getPermissions() != null) {
            role.getPermissions().forEach(permission ->
                    entity.getPermissions().add(permissionMapper.toEntity(permission))
            );
        }

        return entity;
    }
}