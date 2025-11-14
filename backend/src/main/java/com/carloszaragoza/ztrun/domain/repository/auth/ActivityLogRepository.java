package com.carloszaragoza.ztrun.domain.repository.auth;

import com.carloszaragoza.ztrun.domain.model.auth.ActivityLog;
import java.util.List;

public interface ActivityLogRepository {
    ActivityLog save(ActivityLog log);
    List<ActivityLog> findByUserId(Long userId);
}