package com.carloszaragoza.ztrun.infrastructure.persistence.mapper;

import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.CalendarEventEntity;
import org.springframework.stereotype.Component;

@Component
public class CalendarEventMapper {
    public CalendarEvent toDomain(CalendarEventEntity entity) {
        if (entity == null)
            return null;
        return new CalendarEvent(
                entity.getId(),
                entity.getUserId(),
                entity.getGoalId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.isAllDay(),
                entity.getLocation(),
                entity.getStatus(),
                entity.getEventType(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public CalendarEventEntity toEntity(CalendarEvent domain) {
        if (domain == null)
            return null;
        CalendarEventEntity entity = new CalendarEventEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setGoalId(domain.getGoalId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setStartTime(domain.getStartTime());
        entity.setEndTime(domain.getEndTime());
        entity.setAllDay(domain.isAllDay());
        entity.setLocation(domain.getLocation());
        entity.setStatus(domain.getStatus());
        entity.setEventType(domain.getEventType());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }
}
