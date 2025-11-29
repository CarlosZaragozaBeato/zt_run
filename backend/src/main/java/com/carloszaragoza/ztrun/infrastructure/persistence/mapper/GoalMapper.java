package com.carloszaragoza.ztrun.infrastructure.persistence.mapper;

import com.carloszaragoza.ztrun.domain.model.Goal;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.GoalEntity;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {
    public Goal toDomain(GoalEntity entity) {
        if (entity == null)
            return null;
        return new Goal(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTargetDate(),
                entity.getStatus(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getType(),
                entity.getCategory(),
                entity.getPriority(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getProgress(),
                entity.getParentGoalId());
    }

    public GoalEntity toEntity(Goal domain) {
        if (domain == null)
            return null;
        GoalEntity entity = new GoalEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setTargetDate(domain.getTargetDate());
        entity.setStatus(domain.getStatus());
        entity.setUserId(domain.getUserId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setType(domain.getType());
        entity.setCategory(domain.getCategory());
        entity.setPriority(domain.getPriority());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setProgress(domain.getProgress());
        entity.setParentGoalId(domain.getParentGoalId());
        return entity;
    }
}
