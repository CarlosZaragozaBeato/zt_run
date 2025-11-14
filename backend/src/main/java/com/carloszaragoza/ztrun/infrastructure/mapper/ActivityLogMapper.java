package com.carloszaragoza.ztrun.infrastructure.mapper;

import com.carloszaragoza.ztrun.domain.model.auth.ActivityLog;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.ActivityLogEntity;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogMapper {

    public ActivityLog toDomain(ActivityLogEntity entity) {
        if (entity == null) return null;

        ActivityLog log = ActivityLog.create(
                entity.getUserId(),
                entity.getAction(),
                entity.getIpAddress()
        );
        log.setId(entity.getId());
        log.setCreatedAt(entity.getCreatedAt());

        return log;
    }

    public ActivityLogEntity toEntity(ActivityLog log) {
        if (log == null) return null;

        ActivityLogEntity entity = new ActivityLogEntity();
        entity.setId(log.getId());
        entity.setUserId(log.getUserId());
        entity.setAction(log.getAction());
        entity.setIpAddress(log.getIpAddress());
        entity.setCreatedAt(log.getCreatedAt());

        return entity;
    }
}