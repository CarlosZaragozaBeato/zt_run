package com.carloszaragoza.ztrun.infrastructure.persistence.repository;

import com.carloszaragoza.ztrun.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, Long> {}
