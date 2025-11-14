package com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.adapter.ouput.persistence.entity.auth.ActivityLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaActivityLogRepository extends JpaRepository<ActivityLogEntity, Long> {
    List<ActivityLogEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}