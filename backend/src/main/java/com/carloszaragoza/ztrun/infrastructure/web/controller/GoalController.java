package com.carloszaragoza.ztrun.infrastructure.web.controller;

import com.carloszaragoza.ztrun.application.service.GoalService;
import com.carloszaragoza.ztrun.domain.model.Goal;
import com.carloszaragoza.ztrun.infrastructure.web.dto.GoalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto goalDto) {
        Goal createdGoal = goalService.createGoal(goalDto.toDomain());
        return ResponseEntity.ok(GoalDto.fromDomain(createdGoal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoalById(@PathVariable Long id) {
        return goalService.getGoalById(id)
                .map(GoalDto::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GoalDto>> getGoalsByUserId(@PathVariable Long userId) {
        List<GoalDto> goals = goalService.getGoalsByUserId(userId).stream()
                .map(GoalDto::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long id, @RequestBody GoalDto goalDto) {
        try {
            Goal updatedGoal = goalService.updateGoal(id, goalDto.toDomain());
            return ResponseEntity.ok(GoalDto.fromDomain(updatedGoal));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
