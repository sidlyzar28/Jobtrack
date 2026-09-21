package com.jobtrack.jobtrack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobtrack.jobtrack.entity.Job;
import com.jobtrack.jobtrack.entity.JobStatus;

public interface JobRepository extends JpaRepository<Job, Long> {

   List<Job> findByStatusOrderByIdDesc(JobStatus status);

   List<Job> findByCompanyContainingIgnoreCaseOrderByIdDesc(String company);

    long countByStatus(JobStatus status);
}