package com.carloszaragoza.ztrun.domain.model.auth;

import java.time.LocalDateTime;

public class ActivityLog {
    private Long id;
    private Long userId;
    private String action;
    private LocalDateTime createdAt;
    private String ipAddress;

    private ActivityLog() {
        this.createdAt = LocalDateTime.now();
    }

    public static ActivityLog create(Long userId, String action, String ipAddress) {
        ActivityLog log = new ActivityLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setIpAddress(ipAddress);
        return log;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
