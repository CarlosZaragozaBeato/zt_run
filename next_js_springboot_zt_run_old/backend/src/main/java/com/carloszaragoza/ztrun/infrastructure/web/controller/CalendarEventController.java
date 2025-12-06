package com.carloszaragoza.ztrun.infrastructure.web.controller;

import com.carloszaragoza.ztrun.application.service.CalendarEventService;
import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import com.carloszaragoza.ztrun.infrastructure.web.dto.CalendarEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarEventController {
    private final CalendarEventService calendarEventService;

    @PostMapping
    public ResponseEntity<CalendarEventDto> createEvent(@RequestBody CalendarEventDto eventDto) {
        CalendarEvent createdEvent = calendarEventService.createEvent(eventDto.toDomain());
        return ResponseEntity.ok(CalendarEventDto.fromDomain(createdEvent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEventDto> getEventById(@PathVariable Long id) {
        return calendarEventService.getEventById(id)
                .map(CalendarEventDto::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CalendarEventDto>> getEventsByUserId(@PathVariable Long userId) {
        List<CalendarEventDto> events = calendarEventService.getEventsByUserId(userId).stream()
                .map(CalendarEventDto::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEventDto> updateEvent(@PathVariable Long id, @RequestBody CalendarEventDto eventDto) {
        try {
            CalendarEvent updatedEvent = calendarEventService.updateEvent(id, eventDto.toDomain());
            return ResponseEntity.ok(CalendarEventDto.fromDomain(updatedEvent));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        calendarEventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
