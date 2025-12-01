package com.carloszaragoza.ztrun.infrastructure.web.dto;

import com.carloszaragoza.ztrun.domain.model.Goal;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GoalDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime targetDate;
    private Goal.GoalStatus status;
    private Long userId;
    private Goal.GoalType type;
    private Goal.GoalCategory category;
    private Goal.GoalPriority priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer progress;
    private Long parentGoalId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GoalDto fromDomain(Goal goal) {
        if (goal == null)
            return null;
        GoalDto dto = new GoalDto();
        dto.setId(goal.getId());
        dto.setName(goal.getName());
        dto.setDescription(goal.getDescription());
        dto.setTargetDate(goal.getTargetDate());
        dto.setStatus(goal.getStatus());
        dto.setUserId(goal.getUserId());
        dto.setType(goal.getType());
        dto.setCategory(goal.getCategory());
        dto.setPriority(goal.getPriority());
        dto.setStartDate(goal.getStartDate());
        dto.setEndDate(goal.getEndDate());
        dto.setProgress(goal.getProgress());
        dto.setParentGoalId(goal.getParentGoalId());
        dto.setCreatedAt(goal.getCreatedAt());
        dto.setUpdatedAt(goal.getUpdatedAt());
        return dto;
    }

    public Goal toDomain() {
        Goal goal = new Goal();
        goal.setId(this.id);
        goal.setName(this.name);
        goal.setDescription(this.description);
        goal.setTargetDate(this.targetDate);
        goal.setStatus(this.status);
        goal.setUserId(this.userId);
        goal.setType(this.type);
        goal.setCategory(this.category);
        goal.setPriority(this.priority);
        goal.setStartDate(this.startDate);
        goal.setEndDate(this.endDate);
        goal.setProgress(this.progress);
        goal.setParentGoalId(this.parentGoalId);
        return goal;
    }
}
