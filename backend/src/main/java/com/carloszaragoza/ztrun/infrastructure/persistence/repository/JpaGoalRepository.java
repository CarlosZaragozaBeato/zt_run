package com.carloszaragoza.ztrun.infrastructure.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.persistence.entity.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaGoalRepository extends JpaRepository<GoalEntity, Long> {
    List<GoalEntity> findAllByUserId(Long userId);
}
