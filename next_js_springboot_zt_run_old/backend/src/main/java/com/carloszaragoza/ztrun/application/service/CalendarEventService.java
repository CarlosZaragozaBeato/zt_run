package com.carloszaragoza.ztrun.application.service;

import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import com.carloszaragoza.ztrun.domain.port.CalendarEventRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarEventService {
    private final CalendarEventRepositoryPort calendarEventRepositoryPort;

    public CalendarEvent createEvent(CalendarEvent event) {
        return calendarEventRepositoryPort.save(event);
    }

    public Optional<CalendarEvent> getEventById(Long id) {
        return calendarEventRepositoryPort.findById(id);
    }

    public List<CalendarEvent> getEventsByUserId(Long userId) {
        return calendarEventRepositoryPort.findAllByUserId(userId);
    }

    public CalendarEvent updateEvent(Long id, CalendarEvent eventDetails) {
        return calendarEventRepositoryPort.findById(id)
                .map(existingEvent -> {
                    existingEvent.setTitle(eventDetails.getTitle());
                    existingEvent.setDescription(eventDetails.getDescription());
                    existingEvent.setStartTime(eventDetails.getStartTime());
                    existingEvent.setEndTime(eventDetails.getEndTime());
                    existingEvent.setAllDay(eventDetails.isAllDay());
                    existingEvent.setLocation(eventDetails.getLocation());
                    existingEvent.setStatus(eventDetails.getStatus());
                    existingEvent.setEventType(eventDetails.getEventType());
                    existingEvent.setGoalId(eventDetails.getGoalId());
                    return calendarEventRepositoryPort.save(existingEvent);
                }).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public void deleteEvent(Long id) {
        calendarEventRepositoryPort.deleteById(id);
    }
}
