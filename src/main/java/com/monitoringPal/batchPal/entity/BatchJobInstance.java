package com.monitoringPal.batchPal.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BatchJobInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOB_INSTANCE_ID")
    private Long jobInstanceId;

    @Column(name = "JOB_NAME")
    private String jobName;
}