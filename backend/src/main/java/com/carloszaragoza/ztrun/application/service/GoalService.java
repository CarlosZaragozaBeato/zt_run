package com.carloszaragoza.ztrun.application.service;

import com.carloszaragoza.ztrun.domain.model.Goal;
import com.carloszaragoza.ztrun.domain.port.GoalRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepositoryPort goalRepositoryPort;

    public Goal createGoal(Goal goal) {
        return goalRepositoryPort.save(goal);
    }

    public Optional<Goal> getGoalById(Long id) {
        return goalRepositoryPort.findById(id);
    }

    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepositoryPort.findAllByUserId(userId);
    }

    public Goal updateGoal(Long id, Goal goalDetails) {
        return goalRepositoryPort.findById(id)
                .map(existingGoal -> {
                    existingGoal.setName(goalDetails.getName());
                    existingGoal.setDescription(goalDetails.getDescription());
                    existingGoal.setTargetDate(goalDetails.getTargetDate());
                    existingGoal.setStatus(goalDetails.getStatus());
                    existingGoal.setType(goalDetails.getType());
                    existingGoal.setCategory(goalDetails.getCategory());
                    existingGoal.setPriority(goalDetails.getPriority());
                    existingGoal.setStartDate(goalDetails.getStartDate());
                    existingGoal.setEndDate(goalDetails.getEndDate());
                    existingGoal.setProgress(goalDetails.getProgress());
                    existingGoal.setParentGoalId(goalDetails.getParentGoalId());
                    return goalRepositoryPort.save(existingGoal);
                }).orElseThrow(() -> new RuntimeException("Goal not found"));
    }

    public void deleteGoal(Long id) {
        goalRepositoryPort.deleteById(id);
    }
}
