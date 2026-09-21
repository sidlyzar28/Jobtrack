package com.jobtrack.jobtrack.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobtrack.jobtrack.dto.JobStats;
import com.jobtrack.jobtrack.entity.ApplicationStatusHistory;
import com.jobtrack.jobtrack.entity.Job;
import com.jobtrack.jobtrack.entity.JobStatus;
import com.jobtrack.jobtrack.service.JobService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/jobs")
public class JobController {


    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public Job createJob(@Valid @RequestBody Job job) {
        return jobService.createJob(job);
    }

    @GetMapping
public List<Job> searchJobs(
        @RequestParam(required = false) String company,
        @RequestParam(required = false) JobStatus status) {

    return jobService.searchJobs(company, status);
}

   @GetMapping("/stats")
public JobStats getJobStats() {
    return jobService.getJobStats();
}

    @GetMapping("/{id}/history")
public List<ApplicationStatusHistory> getJobHistory(@PathVariable Long id) {
    return jobService.getJobHistory(id);
}

@GetMapping("/{id}")
public ResponseEntity<Job> getJobById(@PathVariable Long id) {
    return jobService.getJobById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    @PutMapping("/{id}")
public ResponseEntity<Job> updateJob(
        @PathVariable Long id,
        @Valid @RequestBody Job job) {

    Job updatedJob = jobService.updateJob(id, job);

    if (updatedJob == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedJob);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

}

