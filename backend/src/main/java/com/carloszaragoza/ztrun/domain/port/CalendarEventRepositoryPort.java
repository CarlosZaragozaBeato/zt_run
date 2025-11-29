package com.carloszaragoza.ztrun.domain.port;

import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import java.util.List;
import java.util.Optional;

public interface CalendarEventRepositoryPort {
    CalendarEvent save(CalendarEvent event);

    Optional<CalendarEvent> findById(Long id);

    List<CalendarEvent> findAllByUserId(Long userId);

    void deleteById(Long id);
}
