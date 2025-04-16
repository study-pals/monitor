package com.monitoringPal.batchPal.service;

import com.monitoringPal.batchPal.dao.BatchJobExecutionRepository;
import com.monitoringPal.batchPal.dto.LogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchLogService {

    private final BatchJobExecutionRepository repository;

    public Page<LogResponse> getLogs(Pageable pageable) {
        return repository.findLogResponses(pageable);
    }
}