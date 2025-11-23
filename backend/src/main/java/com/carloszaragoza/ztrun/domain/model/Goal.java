package com.carloszaragoza.ztrun.domain.model;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goal {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime targetDate;
    private GoalStatus status;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GoalType type;
    private GoalCategory category;
    private GoalPriority priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer progress;
    private Long parentGoalId;

    public enum GoalStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public enum GoalType {
        DAILY, WEEKLY, MONTHLY, LONG_TERM
    }

    public enum GoalCategory {
        FITNESS, WORK, LEARNING, PERSONAL, OTHER
    }

    public enum GoalPriority {
        LOW, MEDIUM, HIGH
    }
}
