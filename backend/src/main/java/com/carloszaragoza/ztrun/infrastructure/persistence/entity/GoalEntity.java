package com.carloszaragoza.ztrun.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.carloszaragoza.ztrun.domain.model.Goal;

@Entity
@Table(name = "goals")
@Getter
@Setter
public class GoalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Goal.GoalStatus status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Goal.GoalType type;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Goal.GoalCategory category;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Goal.GoalPriority priority;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private Integer progress;

    @Column(name = "parent_goal_id")
    private Long parentGoalId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
