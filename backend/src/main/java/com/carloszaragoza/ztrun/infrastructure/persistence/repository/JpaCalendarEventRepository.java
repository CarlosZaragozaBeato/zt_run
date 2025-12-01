package com.carloszaragoza.ztrun.infrastructure.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.persistence.entity.CalendarEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaCalendarEventRepository extends JpaRepository<CalendarEventEntity, Long> {
    List<CalendarEventEntity> findAllByUserId(Long userId);
}
