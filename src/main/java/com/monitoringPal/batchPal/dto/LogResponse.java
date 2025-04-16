package com.monitoringPal.batchPal.dto;

import java.time.LocalDateTime;

public record LogResponse(
        Long jobExecutionId,
        String jobName,
        String status,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
