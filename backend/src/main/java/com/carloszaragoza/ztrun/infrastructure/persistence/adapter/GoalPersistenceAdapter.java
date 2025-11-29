package com.carloszaragoza.ztrun.infrastructure.persistence.adapter;

import com.carloszaragoza.ztrun.domain.model.Goal;
import com.carloszaragoza.ztrun.domain.port.GoalRepositoryPort;
import com.carloszaragoza.ztrun.infrastructure.persistence.entity.GoalEntity;
import com.carloszaragoza.ztrun.infrastructure.persistence.mapper.GoalMapper;
import com.carloszaragoza.ztrun.infrastructure.persistence.repository.JpaGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GoalPersistenceAdapter implements GoalRepositoryPort {
    private final JpaGoalRepository jpaGoalRepository;
    private final GoalMapper goalMapper;

    @Override
    public Goal save(Goal goal) {
        GoalEntity entity = goalMapper.toEntity(goal);
        GoalEntity savedEntity = jpaGoalRepository.save(entity);
        return goalMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Goal> findById(Long id) {
        return jpaGoalRepository.findById(id).map(goalMapper::toDomain);
    }

    @Override
    public List<Goal> findAllByUserId(Long userId) {
        return jpaGoalRepository.findAllByUserId(userId).stream()
                .map(goalMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaGoalRepository.deleteById(id);
    }
}
