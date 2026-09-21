package com.jobtrack.jobtrack.service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobtrack.jobtrack.dto.JobStats;
import com.jobtrack.jobtrack.entity.ApplicationStatusHistory;
import com.jobtrack.jobtrack.entity.Job;
import com.jobtrack.jobtrack.entity.JobStatus;
import com.jobtrack.jobtrack.repository.ApplicationStatusHistoryRepository;
  import com.jobtrack.jobtrack.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final ApplicationStatusHistoryRepository historyRepository;

    public JobService(
        JobRepository jobRepository,
        ApplicationStatusHistoryRepository historyRepository) {

    this.jobRepository = jobRepository;
    this.historyRepository = historyRepository;
}

    public Job createJob(Job job) {

    Job savedJob = jobRepository.save(job);

    ApplicationStatusHistory history =
            new ApplicationStatusHistory(
                    savedJob,
                    savedJob.getStatus(),
                    LocalDateTime.now()
            );

    historyRepository.save(history);

    return savedJob;
}

    public List<Job> getAllJobs() {
    return jobRepository.findAll(
        org.springframework.data.domain.Sort.by(
            org.springframework.data.domain.Sort.Direction.DESC,
            "id"
        )
    );
}

    public List<Job> searchJobs(String company, JobStatus status) {

    if (company != null && !company.isBlank()) {
        return jobRepository.findByCompanyContainingIgnoreCaseOrderByIdDesc(company);
    }

    if (status != null) {
        return jobRepository.findByStatusOrderByIdDesc(status);
    }

    return jobRepository.findAll(
        org.springframework.data.domain.Sort.by(
                org.springframework.data.domain.Sort.Direction.DESC,
                "id"
        )
);
}

    public Optional<Job> getJobById(Long id){
        return jobRepository.findById(id);
    }
    public Job updateJob(Long id, Job updatedJob) {
    return jobRepository.findById(id)
            .map(existingJob -> {

                                JobStatus oldStatus = existingJob.getStatus();
                JobStatus newStatus = updatedJob.getStatus();

                existingJob.setCompany(updatedJob.getCompany());
                existingJob.setJobTitle(updatedJob.getJobTitle());
                existingJob.setLocation(updatedJob.getLocation());
                existingJob.setStatus(newStatus);
                existingJob.setAppliedDate(updatedJob.getAppliedDate());
                existingJob.setJobUrl(updatedJob.getJobUrl());
                existingJob.setJobDescription(updatedJob.getJobDescription());
                existingJob.setNotes(updatedJob.getNotes());

                Job savedJob = jobRepository.save(existingJob);

                if (oldStatus != newStatus) {

                    ApplicationStatusHistory history =
                            new ApplicationStatusHistory(
                                    savedJob,
                                    newStatus,
                                    LocalDateTime.now()
                            );

                    historyRepository.save(history);
                }

                return savedJob;
            })
            .orElse(null);

}
    @Transactional
public void deleteJob(Long id) {
    Job job = jobRepository.findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException("Job not found"));

    historyRepository.deleteByJob(job);
    jobRepository.delete(job);
}

    public JobStats getJobStats() {

    return new JobStats(
            jobRepository.count(),
            jobRepository.countByStatus(JobStatus.APPLIED),
            jobRepository.countByStatus(JobStatus.ASSESSMENT),
            jobRepository.countByStatus(JobStatus.INTERVIEW),
            jobRepository.countByStatus(JobStatus.OFFER),
            jobRepository.countByStatus(JobStatus.REJECTED)
    );
}
public List<ApplicationStatusHistory> getJobHistory(Long jobId) {

    return jobRepository.findById(jobId)
            .map(historyRepository::findByJobOrderByChangedAtAsc)
            .orElse(List.of());
}
}