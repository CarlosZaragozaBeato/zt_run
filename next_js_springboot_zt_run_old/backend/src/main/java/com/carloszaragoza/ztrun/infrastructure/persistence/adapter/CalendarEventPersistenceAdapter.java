package com.carloszaragoza.ztrun.infrastructure.persistence.adapter;

import com.carloszaragoza.ztrun.domain.model.CalendarEvent;
import com.carloszaragoza.ztrun.domain.port.CalendarEventRepositoryPort;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.CalendarEventEntity;
import com.carloszaragoza.ztrun.infrastructure.persistence.mapper.CalendarEventMapper;
import com.carloszaragoza.ztrun.infrastructure.persistence.repository.JpaCalendarEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CalendarEventPersistenceAdapter implements CalendarEventRepositoryPort {
    private final JpaCalendarEventRepository jpaCalendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;

    @Override
    public CalendarEvent save(CalendarEvent event) {
        CalendarEventEntity entity = calendarEventMapper.toEntity(event);
        CalendarEventEntity savedEntity = jpaCalendarEventRepository.save(entity);
        return calendarEventMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CalendarEvent> findById(Long id) {
        return jpaCalendarEventRepository.findById(id).map(calendarEventMapper::toDomain);
    }

    @Override
    public List<CalendarEvent> findAllByUserId(Long userId) {
        return jpaCalendarEventRepository.findAllByUserId(userId).stream()
                .map(calendarEventMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaCalendarEventRepository.deleteById(id);
    }
}
