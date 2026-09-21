package com.jobtrack.jobtrack.dto;

import jakarta.validation.constraints.NotBlank;

public class ResumeMatchRequest {

    @NotBlank(message = "Resume cannot be empty")
    private String resume;

    @NotBlank(message = "Job description cannot be empty")
    private String jobDescription;

    public ResumeMatchRequest() {}

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}