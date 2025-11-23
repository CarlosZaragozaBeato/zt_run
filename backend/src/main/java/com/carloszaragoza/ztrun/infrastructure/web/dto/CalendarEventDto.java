package com.carloszaragoza.ztrun.infrastructure.web.dto;

import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CalendarEventDto {
    private Long id;
    private Long userId;
    private Long goalId;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isAllDay;
    private String location;
    private CalendarEvent.EventStatus status;
    private CalendarEvent.EventType eventType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CalendarEventDto fromDomain(CalendarEvent event) {
        if (event == null)
            return null;
        CalendarEventDto dto = new CalendarEventDto();
        dto.setId(event.getId());
        dto.setUserId(event.getUserId());
        dto.setGoalId(event.getGoalId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setAllDay(event.isAllDay());
        dto.setLocation(event.getLocation());
        dto.setStatus(event.getStatus());
        dto.setEventType(event.getEventType());
        dto.setCreatedAt(event.getCreatedAt());
        dto.setUpdatedAt(event.getUpdatedAt());
        return dto;
    }

    public CalendarEvent toDomain() {
        CalendarEvent event = new CalendarEvent();
        event.setId(this.id);
        event.setUserId(this.userId);
        event.setGoalId(this.goalId);
        event.setTitle(this.title);
        event.setDescription(this.description);
        event.setStartTime(this.startTime);
        event.setEndTime(this.endTime);
        event.setAllDay(this.isAllDay);
        event.setLocation(this.location);
        event.setStatus(this.status);
        event.setEventType(this.eventType);
        return event;
    }
}
