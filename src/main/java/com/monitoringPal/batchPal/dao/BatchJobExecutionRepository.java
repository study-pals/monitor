package com.monitoringPal.batchPal.dao;

import com.monitoringPal.batchPal.dto.LogResponse;
import com.monitoringPal.batchPal.entity.BatchJobExecution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchJobExecutionRepository extends JpaRepository<BatchJobExecution, Long> {

    @Query("SELECT new com.monitoringPal.batchPal.dto.LogResponse(" +
           "e.jobExecutionId, i.jobName, e.status, e.startTime, e.endTime) " +
           "FROM BatchJobExecution e " +
           "JOIN e.jobInstance i " +
           "ORDER BY e.startTime DESC")
    Page<LogResponse> findLogResponses(Pageable pageable);
}