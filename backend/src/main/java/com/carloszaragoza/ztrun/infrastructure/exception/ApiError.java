package com.carloszaragoza.ztrun.infrastructure.exception;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String message,
        LocalDateTime timestamp
) {}
