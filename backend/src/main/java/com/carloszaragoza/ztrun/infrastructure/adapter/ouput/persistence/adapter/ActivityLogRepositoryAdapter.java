package com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.adapter;

import com.carloszaragoza.ztrun.domain.model.auth.ActivityLog;
import com.carloszaragoza.ztrun.domain.repository.auth.ActivityLogRepository;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.ActivityLogEntity;
import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.repository.JpaActivityLogRepository;
import com.carloszaragoza.ztrun.infrastructure.mapper.ActivityLogMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityLogRepositoryAdapter implements ActivityLogRepository {

    private final JpaActivityLogRepository jpaActivityLogRepository;
    private final ActivityLogMapper activityLogMapper;

    public ActivityLogRepositoryAdapter(JpaActivityLogRepository jpaActivityLogRepository,
                                        ActivityLogMapper activityLogMapper) {
        this.jpaActivityLogRepository = jpaActivityLogRepository;
        this.activityLogMapper = activityLogMapper;
    }

    @Override
    public ActivityLog save(ActivityLog log) {
        ActivityLogEntity entity = activityLogMapper.toEntity(log);
        ActivityLogEntity saved = jpaActivityLogRepository.save(entity);
        return activityLogMapper.toDomain(saved);
    }

    @Override
    public List<ActivityLog> findByUserId(Long userId) {
        return jpaActivityLogRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(activityLogMapper::toDomain)
                .collect(Collectors.toList());
    }
}