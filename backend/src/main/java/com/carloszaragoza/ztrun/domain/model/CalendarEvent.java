package com.carloszaragoza.ztrun.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalendarEvent {
    private Long id;
    private Long userId;
    private Long goalId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAllDay;
    private String location;
    private EventStatus status;
    private EventType eventType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum EventStatus {
        SCHEDULED, COMPLETED, CANCELLED
    }

    public enum EventType {
        WORKOUT, MEETING, REMINDER, LEARNING, OTHER
    }
}
