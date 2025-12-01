package com.carloszaragoza.ztrun.domain.port;

import com.carloszaragoza.ztrun.domain.model.Goal;
import java.util.List;
import java.util.Optional;

public interface GoalRepositoryPort {
    Goal save(Goal goal);

    Optional<Goal> findById(Long id);

    List<Goal> findAllByUserId(Long userId);

    void deleteById(Long id);
}
