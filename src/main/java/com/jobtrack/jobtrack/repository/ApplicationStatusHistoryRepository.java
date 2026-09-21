package com.jobtrack.jobtrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtrack.jobtrack.entity.ApplicationStatusHistory;
import com.jobtrack.jobtrack.entity.Job;

public interface ApplicationStatusHistoryRepository
        extends JpaRepository<ApplicationStatusHistory, Long> {

    List<ApplicationStatusHistory> findByJobOrderByChangedAtAsc(Job job);
}