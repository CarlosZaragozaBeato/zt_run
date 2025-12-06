package com.carloszaragoza.ztrun.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.carloszaragoza.ztrun.domain.model.CalendarEvent;

@Entity
@Table(name = "calendar_events")
@Getter
@Setter
public class CalendarEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "goal_id")
    private Long goalId;

    @Column(nullable = false, length = 100)
    private String title;

    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "is_all_day")
    private boolean isAllDay;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CalendarEvent.EventStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", length = 20)
    private CalendarEvent.EventType eventType;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
