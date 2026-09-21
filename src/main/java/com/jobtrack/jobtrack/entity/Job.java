package com.jobtrack.jobtrack.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @NotBlank
private String company;

@NotBlank
private String jobTitle;

@NotBlank
private String location;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private LocalDate appliedDate;

    private String jobUrl;

    @Column(columnDefinition = "TEXT")
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobStatus getStatus() {
    return status;
}

public void setStatus(JobStatus status) {
    this.status = status;
}

public LocalDate getAppliedDate() {
    return appliedDate;
}

public void setAppliedDate(LocalDate appliedDate) {
    this.appliedDate = appliedDate;
}

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getJobDescription() {
    return jobDescription;
}

    public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
}

}