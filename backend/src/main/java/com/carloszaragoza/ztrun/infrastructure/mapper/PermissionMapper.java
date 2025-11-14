package com.carloszaragoza.ztrun.infrastructure.mapper;

import com.carloszaragoza.ztrun.domain.model.auth.Permission;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public Permission toDomain(PermissionEntity entity) {
        if (entity == null) return null;

        Permission permission = new Permission();
        permission.setId(entity.getId());
        permission.setName(entity.getName());
        permission.setDescription(entity.getDescription());

        return permission;
    }

    public PermissionEntity toEntity(Permission permission) {
        if (permission == null) return null;

        PermissionEntity entity = new PermissionEntity();
        entity.setId(permission.getId());
        entity.setName(permission.getName());
        entity.setDescription(permission.getDescription());

        return entity;
    }
}