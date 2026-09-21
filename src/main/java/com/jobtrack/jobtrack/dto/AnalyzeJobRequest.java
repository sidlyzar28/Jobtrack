package com.jobtrack.jobtrack.dto;

import jakarta.validation.constraints.NotBlank;

public class AnalyzeJobRequest {

    @NotBlank(message = "Job description cannot be empty")
    private String jobDescription;

    public AnalyzeJobRequest() {
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}